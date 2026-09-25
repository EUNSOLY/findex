package com.eunsoly.findex.repository.integration;

import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationHistoryRepositorySupport {

    List<IntegrationHistory> findSyncJobs(IntegrationHistorySearchCondition condition);

    Long count(IntegrationHistorySearchCondition condition);
}
