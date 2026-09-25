package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.domain.entity.index.IndexInformation;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IndexInformationServiceImpl implements IndexInformationService {

    @Override
    public IndexInformation upsert(IndexInformation entity) {
        return null;
    }

    @Override
    public List<IndexInformation> findSyncTargets(List<Long> ids) {
        return List.of();
    }
}
