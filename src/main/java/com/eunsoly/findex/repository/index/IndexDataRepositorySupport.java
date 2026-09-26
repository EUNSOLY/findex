package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.IndexData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexDataRepositorySupport {
    List<IndexData> searchIndexData(IndexDataSearchCondition condition);

    Long count(IndexDataSearchCondition condition);

}
