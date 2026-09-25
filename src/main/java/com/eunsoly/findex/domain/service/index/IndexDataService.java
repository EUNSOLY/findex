package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.domain.entity.index.IndexData;

public interface IndexDataService {

    IndexData upsert(IndexData entity);
}
