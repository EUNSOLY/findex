package com.eunsoly.findex.domain.service.integration;

import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import com.eunsoly.findex.repository.integration.IntegrationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegrationHistoryServiceImpl implements IntegrationHistoryService {
    private final IntegrationHistoryRepository integrationHistoryRepository;

    @Override
    public IntegrationHistory saveSuccess(IntegrationHistory entity) {
        return integrationHistoryRepository.save(entity);
    }

    @Override
    public IntegrationHistory saveFailed(IntegrationHistory entity) {
        return integrationHistoryRepository.save(entity);
    }
}
