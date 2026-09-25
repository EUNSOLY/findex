package com.eunsoly.findex.domain.service.integration;

import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import com.eunsoly.findex.repository.integration.IntegrationHistoryRepository;
import com.eunsoly.findex.repository.integration.IntegrationHistorySearchCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegrationHistoryServiceImpl implements IntegrationHistoryService {
    private final IntegrationHistoryRepository integrationHistoryRepository;

    @Override
    public IntegrationHistoryCursorResult findSyncJobHistories(
            IntegrationHistorySearchCondition condition) {

        List<IntegrationHistory> histories = integrationHistoryRepository.findSyncJobs(condition);
        Long totalElements = integrationHistoryRepository.count(condition);

        Long nextIdAfter = null;
        String nextCursor = null;
        boolean hashNext = histories.size() > condition.size();

        List<IntegrationHistory> resultHistories =
                histories.subList(0, Math.min(histories.size(), condition.size()));

        if (!histories.isEmpty()) {
            IntegrationHistory lastEntity = resultHistories.getLast();
            nextIdAfter = lastEntity.getId();
            nextCursor = this.getLastSortValue(condition.sortField(), lastEntity);
        }

        CursorPaginationResult cursorPaginationResult =
                CursorPaginationResult.of(
                        nextCursor, nextIdAfter, condition.size(), totalElements, hashNext);

        return new IntegrationHistoryCursorResult(resultHistories, cursorPaginationResult);
    }

    private String getLastSortValue(String sortField, IntegrationHistory integrationHistory) {
        if (sortField.equals("targetDate")) {
            return integrationHistory.getTargetDate().toString();
        }
        return integrationHistory.getJobTime().toString();
    }

    @Override
    public IntegrationHistory saveSuccess(IntegrationHistory entity) {
        return integrationHistoryRepository.save(entity);
    }

    @Override
    public IntegrationHistory saveFailed(IntegrationHistory entity) {
        return integrationHistoryRepository.save(entity);
    }
}
