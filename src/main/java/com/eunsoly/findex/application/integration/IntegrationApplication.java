package com.eunsoly.findex.application.integration;

import com.eunsoly.findex.application.index.dto.CreateIndexDataCommand;
import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.index.port.ExternalIndexProvider;
import com.eunsoly.findex.application.integration.dto.IndexDataPage;
import com.eunsoly.findex.application.integration.dto.IntegrationHistoryCommand;
import com.eunsoly.findex.application.integration.dto.SyncIndexDataCommand;
import com.eunsoly.findex.application.integration.dto.SyncIndexResult;
import com.eunsoly.findex.domain.entity.index.IndexData;
import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import com.eunsoly.findex.domain.service.index.IndexDataService;
import com.eunsoly.findex.domain.service.index.IndexInformationService;
import com.eunsoly.findex.domain.service.integration.IntegrationHistoryService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationApplication {

    private final String CREATE_TYPE = "OPEN_API";
    private static final int CHUNK_SIZE = 100;
    private final TransactionTemplate transactionTemplate;
    private final ExternalIndexProvider indexInformationProvider;
    private final IndexInformationService indexInformationService;
    private final IntegrationHistoryService integrationHistoryService;
    private final IndexDataService indexDataService;

    public List<SyncIndexResult> syncIndexInfos(String clientIp) {
        List<CreateIndexInformationCommand> indexInfoCommands =
                indexInformationProvider.getOpenApiIndexInfos();
        List<SyncIndexResult> results = new ArrayList<>();
        LocalDateTime jobTime = LocalDateTime.now();

        for (int i = 0; i < indexInfoCommands.size(); i += CHUNK_SIZE) {
            List<CreateIndexInformationCommand> chunk =
                    indexInfoCommands.subList(
                            i, Math.min(i + CHUNK_SIZE, indexInfoCommands.size()));
            try {
                results.addAll(
                        transactionTemplate.execute(
                                status -> this.processIndexInfoChunk(chunk, clientIp, jobTime)));
            } catch (Exception e) {
                log.warn("청크 실패, 1건씩 재시도합니다. index={}", i, e);
                results.addAll(this.processIndexInfoOneByOne(chunk, clientIp, jobTime));
            }
        }
        return results;
    }

    private List<SyncIndexResult> processIndexInfoChunk(
            List<CreateIndexInformationCommand> chunk, String clientIp, LocalDateTime jobTime) {
        List<SyncIndexResult> result = new ArrayList<>();

        for (CreateIndexInformationCommand command : chunk) {
            IndexInformation savedIndexInfo =
                    indexInformationService.upsert(command.toEntity(CREATE_TYPE));
            IntegrationHistoryCommand historyCommand =
                    IntegrationHistoryCommand.of(savedIndexInfo, null, clientIp, jobTime);
            IntegrationHistory history = historyCommand.toEntity("INDEX_INFO", true);
            IntegrationHistory savedHistory = integrationHistoryService.saveSuccess(history);
            result.add(SyncIndexResult.of(savedHistory, savedIndexInfo));
        }

        return result;
    }

    private List<SyncIndexResult> processIndexInfoOneByOne(
            List<CreateIndexInformationCommand> chunk, String clientIp, LocalDateTime jobTime) {
        List<SyncIndexResult> result = new ArrayList<>();

        for (CreateIndexInformationCommand command : chunk) {
            try {
                result.add(
                        transactionTemplate.execute(
                                status -> {
                                    IndexInformation saved =
                                            indexInformationService.upsert(
                                                    command.toEntity(CREATE_TYPE));
                                    IntegrationHistory history =
                                            IntegrationHistoryCommand.of(
                                                            saved, null, clientIp, jobTime)
                                                    .toEntity("INDEX_INFO", true);
                                    return SyncIndexResult.of(
                                            integrationHistoryService.saveSuccess(history), saved);
                                }));
            } catch (Exception e) {
                log.warn(
                        "지수 정보 연동 실패: {} / {}",
                        command.indexClassification(),
                        command.indexName(),
                        e);

                result.add(
                        transactionTemplate.execute(
                                status -> {
                                    IntegrationHistory history =
                                            IntegrationHistoryCommand.of(
                                                            null, null, clientIp, jobTime)
                                                    .toEntity("INDEX_INFO", false);

                                    return SyncIndexResult.of(
                                            integrationHistoryService.saveFailed(history), null);
                                }));
            }
        }

        return result;
    }

    public List<SyncIndexResult> syncIndexData(SyncIndexDataCommand command, String clientIp) {
        LocalDateTime jobTime = LocalDateTime.now();
        // 1. 받은 ids로 일치하는 IndexInformation을 들을 다 가져온다 (List)
        List<IndexInformation> indexInformationTargets =
                indexInformationService.findSyncTargets(command.indexInfoIds());
        List<SyncIndexResult> results = new ArrayList<>();

        for (IndexInformation indexInformation : indexInformationTargets) {
            int pageNo = 1;
            IndexDataPage page;
            String indexName = indexInformation.getIndexName();
            String indexClassification = indexInformation.getIndexClassification();

            do {
                try {
                    // 2. IndexInformation을의 indexName을 OpenAPI에 전달하여 OpenAPI를 호출한다. (반복 호출)
                    page =
                            indexInformationProvider.getOpenApiIndexData(
                                    pageNo,
                                    indexClassification,
                                    indexName,
                                    command.baseDateFrom(),
                                    command.baseDateTo());
                } catch (RuntimeException e) {
                    log.warn("OpenAPI 호출 실패: {} (page {})", indexName, pageNo, e);
                    IntegrationHistory history =
                            IntegrationHistoryCommand.of(indexInformation, null, clientIp, jobTime)
                                    .toEntity("INDEX_DATA", false);

                    results.add(
                            SyncIndexResult.of(
                                    integrationHistoryService.saveFailed(history),
                                    indexInformation));
                    break; // 다음 지수로
                }
                if (!page.items().isEmpty()) {
                    results.addAll(
                            processIndexDataChunk(
                                    indexInformation, page.items(), clientIp, jobTime)); // 트랜잭션
                }

                List<CreateIndexDataCommand> items = page.items();
                for (int i = 0; i < items.size(); i += CHUNK_SIZE) {
                    List<CreateIndexDataCommand> chunk =
                            items.subList(i, Math.min(i + CHUNK_SIZE, items.size()));
                    results.addAll(
                            processIndexDataChunk(indexInformation, chunk, clientIp, jobTime));
                }

                pageNo++;
            } while (page.hasNext());
        }

        return results;
    }

    private List<SyncIndexResult> processIndexDataChunk(
            IndexInformation indexInformation,
            List<CreateIndexDataCommand> items,
            String clientIp,
            LocalDateTime jobTime) {
        try {
            return transactionTemplate.execute(
                    status -> {
                        List<SyncIndexResult> results = new ArrayList<>(); // 람다 안으로
                        for (CreateIndexDataCommand item : items) {
                            IndexData savedIndexData =
                                    indexDataService.upsert(item.toEntity(CREATE_TYPE));
                            IntegrationHistoryCommand historyCommand =
                                    IntegrationHistoryCommand.of(
                                            item.indexInformation(),
                                            savedIndexData.getBaseDate(),
                                            clientIp,
                                            jobTime);
                            IntegrationHistory history =
                                    historyCommand.toEntity("INDEX_DATA", true);
                            IntegrationHistory savedHistory =
                                    integrationHistoryService.saveSuccess(history);
                            results.add(SyncIndexResult.of(savedHistory, item.indexInformation()));
                        }
                        return results;
                    });
        } catch (Exception e) {
            log.warn("지수 데이터 청크 저장 실패, 단건 재시도: {}", indexInformation.getIndexName(), e);
            return processIndexDataOneByOne(indexInformation, items, clientIp, jobTime);
        }
    }

    private List<SyncIndexResult> processIndexDataOneByOne(
            IndexInformation indexInformation,
            List<CreateIndexDataCommand> items,
            String clientIp,
            LocalDateTime jobTime) {
        List<SyncIndexResult> results = new ArrayList<>();

        for (CreateIndexDataCommand command : items) {
            try {
                results.add(
                        transactionTemplate.execute(
                                status -> {
                                    IndexData saved =
                                            indexDataService.upsert(command.toEntity(CREATE_TYPE));
                                    IntegrationHistory history =
                                            IntegrationHistoryCommand.of(
                                                            indexInformation,
                                                            saved.getBaseDate(),
                                                            clientIp,
                                                            jobTime)
                                                    .toEntity("INDEX_DATA", true);
                                    return SyncIndexResult.of(
                                            integrationHistoryService.saveSuccess(history),
                                            indexInformation);
                                }));
            } catch (Exception e) {
                log.warn(
                        "지수 데이터 연동 실패: {} / 기준 일자: {}",
                        indexInformation.getIndexName(),
                        command.baseDate(),
                        e);
                results.add(
                        transactionTemplate.execute(
                                status -> {
                                    IntegrationHistory history =
                                            IntegrationHistoryCommand.of(
                                                            indexInformation,
                                                            command.baseDate(),
                                                            clientIp,
                                                            jobTime)
                                                    .toEntity("INDEX_DATA", false);
                                    return SyncIndexResult.of(
                                            integrationHistoryService.saveFailed(history),
                                            indexInformation);
                                }));
            }
        }
        return results;
    }
}
