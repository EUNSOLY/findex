package com.eunsoly.findex.repository.index;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexInformationRepositorySupport {
    List<IndexInformationSummary> findAllSummaries();
}
