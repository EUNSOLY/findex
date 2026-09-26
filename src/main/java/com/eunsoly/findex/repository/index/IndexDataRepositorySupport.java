package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.IndexData;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IndexDataRepositorySupport {
    List<IndexData> searchIndexData(IndexDataSearchCondition condition);

    Long count(IndexDataSearchCondition condition);

    List<IndexDataRankPair> findTopRank(Long indexInfoId, LocalDate today, LocalDate diff, Integer limit);
}
