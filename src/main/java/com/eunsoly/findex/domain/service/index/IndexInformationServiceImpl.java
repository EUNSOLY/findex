package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.common.exception.base.ErrorCode;
import com.eunsoly.findex.common.exception.index.IndexException;
import com.eunsoly.findex.domain.entity.SourceType;
import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.repository.index.IndexInformationRepository;
import com.eunsoly.findex.repository.index.IndexInformationSearchCondition;
import com.eunsoly.findex.repository.index.IndexInformationSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexInformationServiceImpl implements IndexInformationService {
    private final IndexInformationRepository indexInformationRepository;

    @Override
    public IndexInformation upsert(IndexInformation entity) {
        return indexInformationRepository.findByIndexClassificationAndIndexName(entity.getIndexClassification(), entity.getIndexName())
                .map(existing -> {
                    if (existing.getSourceType().equals(SourceType.USER)) {
                        return existing.updateByUser(entity.getEmployedItemsCount(), entity.getBasePointInTime(), entity.getBaseIndex(),
                                entity.getFavorite());
                    }
                    existing.updateByIntegration(entity.getEmployedItemsCount(), entity.getBasePointInTime(), entity.getBaseIndex());
                    return existing;
                }).orElseGet(() -> indexInformationRepository.save(entity));
    }

    @Override
    public List<IndexInformation> findSyncTargets(List<Long> ids) {
        return List.of();
    }

    @Override
    public IndexInformation findById(Long id) {
        return indexInformationRepository.findById(id)
                .orElseThrow(() -> new IndexException(ErrorCode.INDEX_INFO_NOT_FOUND, String.format("잘못된 지수 정보 입니다. 요청 지수정보 ID : %s", id), null));
    }

    @Override
    public void deleteInformation(Long id) {
        IndexInformation deletedEntity = this.findById(id);
        indexInformationRepository.delete(deletedEntity);
    }

    @Override
    public List<IndexInformationSummary> findSummariesAll() {
        return indexInformationRepository.findAllSummaries();
    }

    @Override
    public IndexInformationCursorResult searchIndexInformations(IndexInformationSearchCondition condition) {

        List<IndexInformation> indexInformations = indexInformationRepository.findIndexInformations(condition);
        Long totalElements = indexInformationRepository.count(condition);

        Long nextIdAfter = null;
        String nextCursor = null;
        boolean hashNext = indexInformations.size() > condition.size();

        List<IndexInformation> resultIndexInformations = indexInformations.subList(0, Math.min(indexInformations.size(), condition.size()));

        if (!indexInformations.isEmpty()) {
            IndexInformation lastEntity = resultIndexInformations.getLast();
            nextIdAfter = lastEntity.getId();
            nextCursor = this.getLastSortValue(condition.sortField(), lastEntity);
        }

        CursorPaginationResult cursorPaginationResult = CursorPaginationResult.of(nextCursor, nextIdAfter, condition.size(), totalElements, hashNext);

        return new IndexInformationCursorResult(resultIndexInformations, cursorPaginationResult);
    }

    private String getLastSortValue(String sortField, IndexInformation indexInformation) {
        return switch (sortField) {
            case "indexName" -> indexInformation.getIndexName();
            case "employedItemsCount" -> String.valueOf(indexInformation.getEmployedItemsCount());
            default -> indexInformation.getIndexClassification();
        };
    }
}
