package com.eunsoly.findex.repository.integration;

import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationHistoryRepository
        extends JpaRepository<IntegrationHistory, Long>, IntegrationHistoryRepositorySupport {}
