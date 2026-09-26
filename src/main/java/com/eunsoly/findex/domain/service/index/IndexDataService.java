package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.domain.entity.index.IndexData;
import com.eunsoly.findex.repository.index.IndexDataSearchCondition;

public interface IndexDataService {

    IndexData upsert(IndexData entity);

    IndexDataCursorResult searchIndexData(IndexDataSearchCondition condition);

    void deleteById(Long id);

    IndexData findById(Long id);

    IndexChartDataResult getChartData(Long indexInformationId, String periodType);
}
