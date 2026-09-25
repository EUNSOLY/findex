package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.QIndexInformation;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IndexInformationRepositorySupportImpl implements IndexInformationRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<IndexInformationSummary> findAllSummaries() {
        QIndexInformation indexInformation = QIndexInformation.indexInformation;

        return jpaQueryFactory.select(Projections.constructor(IndexInformationSummary.class, indexInformation.id,
                indexInformation.indexClassification, indexInformation.indexName)).from(indexInformation).fetch();
    }
}
