package com.eunsoly.findex.application.integration;

import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.integration.dto.IntegrationHistoryCommand;
import com.eunsoly.findex.application.integration.dto.SyncIndexInfoResult;
import com.eunsoly.findex.application.integration.port.IndexInformationProvider;
import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
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
    private final IndexInformationProvider indexInformationProvider;
    private final IndexInformationService indexInformationService;
    private final IntegrationHistoryService integrationHistoryService;

    public List<SyncIndexInfoResult> syncIndexInfos(String clientIp) {
        List<CreateIndexInformationCommand> indexInfoCommands =
                indexInformationProvider.getOpenApiIndexInfos();
        List<SyncIndexInfoResult> results = new ArrayList<>();
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

    private List<SyncIndexInfoResult> processIndexInfoChunk(
            List<CreateIndexInformationCommand> chunk, String clientIp, LocalDateTime jobTime) {
        List<SyncIndexInfoResult> result = new ArrayList<>();

        for (CreateIndexInformationCommand command : chunk) {
            IndexInformation savedIndexInfo =
                    indexInformationService.upsert(command.toEntity(CREATE_TYPE));
            IntegrationHistoryCommand historyCommand =
                    IntegrationHistoryCommand.of(savedIndexInfo, null, clientIp, jobTime);
            IntegrationHistory history = historyCommand.toEntity("INDEX_INFO", true);
            IntegrationHistory savedHistory = integrationHistoryService.saveSuccess(history);
            result.add(SyncIndexInfoResult.of(savedHistory, savedIndexInfo));
        }

        return result;
    }

    private List<SyncIndexInfoResult> processIndexInfoOneByOne(
            List<CreateIndexInformationCommand> chunk, String clientIp, LocalDateTime jobTime) {
        List<SyncIndexInfoResult> result = new ArrayList<>();

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
                                    return SyncIndexInfoResult.of(
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

                                    return SyncIndexInfoResult.of(
                                            integrationHistoryService.saveFailed(history), null);
                                }));
            }
        }

        return result;
    }
}
