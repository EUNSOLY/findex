package com.eunsoly.findex.repository.index;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IndexDataRepositorySupportImpl implements IndexDataRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
}
