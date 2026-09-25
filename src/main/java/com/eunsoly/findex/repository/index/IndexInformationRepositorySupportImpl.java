package com.eunsoly.findex.repository.index;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IndexInformationRepositorySupportImpl implements IndexInformationRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
}
