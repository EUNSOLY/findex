package com.eunsoly.findex.repository.integration;

import com.eunsoly.findex.domain.entity.integration.IntegrationConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationConfigRepository extends JpaRepository<IntegrationConfig, Long>, IntegrationConfigRepositorySupport {
}
