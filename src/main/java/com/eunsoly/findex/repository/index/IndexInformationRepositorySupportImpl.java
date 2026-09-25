package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.domain.entity.index.QIndexInformation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
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

    @Override
    public List<IndexInformation> findIndexInformations(IndexInformationSearchCondition condition) {
        QIndexInformation indexInformation = QIndexInformation.indexInformation;

        return jpaQueryFactory.selectFrom(indexInformation).where(this.filterConditions(condition), this.cursorCondition(condition))
                .orderBy(this.orderSpecifiers(condition)).limit(condition.size() + 1).fetch();
    }

    @Override
    public Long count(IndexInformationSearchCondition condition) {
        QIndexInformation indexInformation = QIndexInformation.indexInformation;

        return jpaQueryFactory.select(indexInformation.count()).from(indexInformation).where(this.filterConditions(condition)).fetchOne();
    }

    // 조건절1.
    private BooleanBuilder filterConditions(IndexInformationSearchCondition condition) {
        QIndexInformation indexInformation = QIndexInformation.indexInformation;
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.indexClassification() != null) {
            builder.and(indexInformation.indexClassification.contains(condition.indexClassification()));
        }
        if (condition.indexName() != null) {
            builder.and(indexInformation.indexName.contains(condition.indexName()));
        }
        if (condition.favorite() != null) {
            builder.and(indexInformation.favorite.eq(condition.favorite()));
        }
        return builder;
    }

    // 컬럼 정렬
    private BooleanExpression cursorCondition(IndexInformationSearchCondition condition) {
        if (condition.cursor() == null) {
            return null;
        }
        QIndexInformation indexInformation = QIndexInformation.indexInformation;
        boolean isDesc = "DESC".equalsIgnoreCase(condition.sortDirection());

        return switch (condition.sortField()) {
            case "indexClassification" -> isDesc
                    ? indexInformation.indexClassification.lt(condition.cursor())
                            .or(indexInformation.indexClassification.eq(condition.cursor()).and(indexInformation.id.lt(condition.idAfter())))
                    : indexInformation.indexClassification.gt(condition.cursor())
                            .or(indexInformation.indexClassification.eq(condition.cursor()).and(indexInformation.id.gt(condition.idAfter())));
            case "indexName" -> isDesc
                    ? indexInformation.indexName.lt(condition.cursor())
                            .or(indexInformation.indexName.eq(condition.cursor()).and(indexInformation.id.lt(condition.idAfter())))
                    : indexInformation.indexName.gt(condition.cursor())
                            .or(indexInformation.indexName.eq(condition.cursor()).and(indexInformation.id.gt(condition.idAfter())));
            case "employedItemsCount" -> {
                Integer employedItemsCount = Integer.parseInt(condition.cursor());
                yield isDesc
                        ? indexInformation.employedItemsCount.lt(employedItemsCount)
                                .or(indexInformation.employedItemsCount.eq(employedItemsCount).and(indexInformation.id.lt(condition.idAfter())))
                        : indexInformation.employedItemsCount.gt(employedItemsCount)
                                .or(indexInformation.employedItemsCount.eq(employedItemsCount).and(indexInformation.id.gt(condition.idAfter())));
            }
            default -> isDesc ? indexInformation.id.lt(condition.idAfter()) : indexInformation.id.gt(condition.idAfter());
        };

    }

    // 정렬
    private OrderSpecifier<?>[] orderSpecifiers(IndexInformationSearchCondition condition) {
        ComparableExpressionBase<?> target = getSortTarget(condition.sortField());
        Order direction = "DESC".equalsIgnoreCase(condition.sortDirection()) ? Order.DESC : Order.ASC;
        QIndexInformation indexInformation = QIndexInformation.indexInformation;

        return new OrderSpecifier<?>[] {new OrderSpecifier<>(direction, target), new OrderSpecifier<>(direction, indexInformation.id)};
    }

    // 정렬 타겟 필드 가져오기
    private ComparableExpressionBase<?> getSortTarget(String sortField) {
        QIndexInformation indexInformation = QIndexInformation.indexInformation;
        return switch (sortField) {
            case "indexName" -> indexInformation.indexName;
            case "employedItemsCount" -> indexInformation.employedItemsCount;
            default -> indexInformation.indexClassification;
        };
    }
}
