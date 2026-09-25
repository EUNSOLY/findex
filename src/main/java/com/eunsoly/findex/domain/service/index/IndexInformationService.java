package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.domain.entity.index.IndexInformation;

import java.util.List;

public interface IndexInformationService {
    // 2-1. 가져온 데이터에서 "index_classification", "index_name"이 동일한 값이 있는지 확인한다.
    // 2-1 에서 값이 있다면 수정한다.
    // 2-1 에서 값이 없다면 및 저장
    IndexInformation upsert(IndexInformation entity);

    List<IndexInformation> findSyncTargets(List<Long> ids);

    IndexInformation findById(Long id);
}
