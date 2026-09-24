package com.eunsoly.findex.repository.integration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntegrationHistoryRepositorySupportImpl
        implements IntegrationHistoryRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
}
