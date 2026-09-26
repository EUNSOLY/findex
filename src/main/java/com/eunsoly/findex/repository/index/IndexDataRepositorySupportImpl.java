package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.IndexData;
import com.eunsoly.findex.domain.entity.index.QIndexData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class IndexDataRepositorySupportImpl implements IndexDataRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<IndexData> searchIndexData(IndexDataSearchCondition condition) {
        QIndexData indexData = QIndexData.indexData;

        return jpaQueryFactory.selectFrom(indexData).where(this.filterConditions(condition), this.cursorCondition(condition))
                .orderBy(this.orderSpecifiers(condition)).limit(condition.size() + 1).fetch();
    }

    @Override
    public Long count(IndexDataSearchCondition condition) {
        QIndexData indexData = QIndexData.indexData;
        return jpaQueryFactory.select(indexData.count()).from(indexData).where(this.filterConditions(condition)).fetchOne();
    }


    // 조건절1.
    private BooleanBuilder filterConditions(IndexDataSearchCondition condition) {
        QIndexData indexData = QIndexData.indexData;
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.indexInfoId() != null) {
            builder.and(indexData.indexInformation.id.eq(condition.indexInfoId()));
        }
        if (condition.startDate() != null) {
            builder.and(indexData.baseDate.goe(condition.startDate()));
        }
        if (condition.endDate() != null) {
            builder.and(indexData.baseDate.loe(condition.endDate()));
        }

        return builder;
    }

    // 컬럼 정렬
    private BooleanExpression cursorCondition(IndexDataSearchCondition condition) {
        if (condition.cursor() == null) {
            return null;
        }

        QIndexData indexData = QIndexData.indexData;
        boolean isDesc = "DESC".equalsIgnoreCase(condition.sortDirection());
        return switch (condition.sortField()) {
            case "baseDate" -> {
                LocalDate cursor = LocalDate.parse(condition.cursor());
                yield isDesc ? indexData.baseDate.lt(cursor).or(indexData.baseDate.eq(cursor).and(indexData.id.lt(condition.idAfter())))
                        : indexData.baseDate.gt(cursor).or(indexData.baseDate.eq(cursor).and(indexData.id.gt(condition.idAfter())));
            }
            case "closingPrice" -> {
                BigDecimal cursor = new BigDecimal(condition.cursor());
                yield isDesc ? indexData.closingPrice.lt(cursor).or(indexData.closingPrice.eq(cursor).and(indexData.id.lt(condition.idAfter())))
                        : indexData.closingPrice.gt(cursor).or(indexData.closingPrice.eq(cursor).and(indexData.id.gt(condition.idAfter())));
            }
            case "highPrice" -> {
                BigDecimal cursor = new BigDecimal(condition.cursor());
                yield isDesc ? indexData.highPrice.lt(cursor).or(indexData.highPrice.eq(cursor).and(indexData.id.lt(condition.idAfter())))
                        : indexData.highPrice.gt(cursor).or(indexData.highPrice.eq(cursor).and(indexData.id.gt(condition.idAfter())));
            }
            case "lowPrice" -> {
                BigDecimal cursor = new BigDecimal(condition.cursor());
                yield isDesc ? indexData.lowPrice.lt(cursor).or(indexData.lowPrice.eq(cursor).and(indexData.id.lt(condition.idAfter())))
                        : indexData.lowPrice.gt(cursor).or(indexData.lowPrice.eq(cursor).and(indexData.id.gt(condition.idAfter())));
            }
            case "versus" -> {
                BigDecimal cursor = new BigDecimal(condition.cursor());
                yield isDesc ? indexData.versus.lt(cursor).or(indexData.versus.eq(cursor).and(indexData.id.lt(condition.idAfter())))
                        : indexData.versus.gt(cursor).or(indexData.versus.eq(cursor).and(indexData.id.gt(condition.idAfter())));
            }
            case "marketPrice" -> {
                BigDecimal cursor = new BigDecimal(condition.cursor());
                yield isDesc ? indexData.marketPrice.lt(cursor).or(indexData.marketPrice.eq(cursor).and(indexData.id.lt(condition.idAfter())))
                        : indexData.marketPrice.gt(cursor).or(indexData.marketPrice.eq(cursor).and(indexData.id.gt(condition.idAfter())));
            }
            case "fluctuationRate" -> {
                BigDecimal cursor = new BigDecimal(condition.cursor());
                yield isDesc ? indexData.fluctuationRate.lt(cursor).or(indexData.fluctuationRate.eq(cursor).and(indexData.id.lt(condition.idAfter())))
                        : indexData.fluctuationRate.gt(cursor).or(indexData.fluctuationRate.eq(cursor).and(indexData.id.gt(condition.idAfter())));
            }
            case "tradingQuantity" -> {
                Long cursor = Long.parseLong(condition.cursor());
                yield isDesc ? indexData.tradingQuantity.lt(cursor).or(indexData.tradingQuantity.eq(cursor).and(indexData.id.lt(condition.idAfter())))
                        : indexData.tradingQuantity.gt(cursor).or(indexData.tradingQuantity.eq(cursor).and(indexData.id.gt(condition.idAfter())));
            }
            default -> isDesc ? indexData.id.lt(condition.idAfter()) : indexData.id.gt(condition.idAfter());
        };
    }

    // 정렬
    private OrderSpecifier<?>[] orderSpecifiers(IndexDataSearchCondition condition) {
        ComparableExpressionBase<?> target = getSortTarget(condition.sortField());
        Order direction = "DESC".equalsIgnoreCase(condition.sortDirection()) ? Order.DESC : Order.ASC;
        QIndexData indexData = QIndexData.indexData;

        return new OrderSpecifier<?>[] {new OrderSpecifier<>(direction, target), new OrderSpecifier<>(direction, indexData.id)};
    }

    // 정렬 타겟 필드 가져오기
    private ComparableExpressionBase<?> getSortTarget(String sortField) {
        QIndexData indexData = QIndexData.indexData;

        return switch (sortField) {
            case "closingPrice" -> indexData.closingPrice;
            case "highPrice" -> indexData.highPrice;
            case "lowPrice" -> indexData.lowPrice;
            case "versus" -> indexData.versus;
            case "marketPrice" -> indexData.marketPrice;
            case "fluctuationRate" -> indexData.fluctuationRate;
            case "tradingQuantity" -> indexData.tradingQuantity;

            default -> indexData.baseDate;
        };
    }
}
