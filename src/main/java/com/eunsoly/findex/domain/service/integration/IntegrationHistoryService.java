package com.eunsoly.findex.domain.service.integration;

import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;

public interface IntegrationHistoryService {

    IntegrationHistory saveSuccess(IntegrationHistory entity);

    IntegrationHistory saveFailed(IntegrationHistory entity);
}
