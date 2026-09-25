package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.IndexInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexInformationRepositorySupport {
    List<IndexInformationSummary> findAllSummaries();

    List<IndexInformation> findIndexInformations(IndexInformationSearchCondition condition);

    Long count(IndexInformationSearchCondition condition);
}
