package com.eunsoly.findex.repository.integration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntegrationConfigRepositorySupportImpl implements IntegrationConfigRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
}
