package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.domain.entity.SourceType;
import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.repository.index.IndexInformationRepository;
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
}
