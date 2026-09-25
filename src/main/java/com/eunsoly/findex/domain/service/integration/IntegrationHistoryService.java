package com.eunsoly.findex.domain.service.integration;

import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import com.eunsoly.findex.repository.integration.IntegrationHistorySearchCondition;

public interface IntegrationHistoryService {

    IntegrationHistoryCursorResult searchSyncJobHistories(IntegrationHistorySearchCondition condition);

    IntegrationHistory saveSuccess(IntegrationHistory entity);

    IntegrationHistory saveFailed(IntegrationHistory entity);
}
