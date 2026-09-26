package com.eunsoly.findex.repository.integration;

import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import com.eunsoly.findex.domain.entity.integration.QIntegrationHistory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class IntegrationHistoryRepositorySupportImpl implements IntegrationHistoryRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<IntegrationHistory> findSyncJobs(IntegrationHistorySearchCondition condition) {
        QIntegrationHistory integrationHistory = QIntegrationHistory.integrationHistory;

        return jpaQueryFactory.selectFrom(integrationHistory).where(this.filterConditions(condition), this.cursorCondition(condition))
                .orderBy(this.orderSpecifiers(condition)).limit(condition.size() + 1).fetch();
    }

    @Override
    public Long count(IntegrationHistorySearchCondition condition) {
        QIntegrationHistory integrationHistory = QIntegrationHistory.integrationHistory;

        return jpaQueryFactory.select(integrationHistory.count()).from(integrationHistory).where(this.filterConditions(condition)).fetchOne();
    }

    // 조건절1.
    private BooleanBuilder filterConditions(IntegrationHistorySearchCondition condition) {
        QIntegrationHistory integrationHistory = QIntegrationHistory.integrationHistory;
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.jobType() != null) {
            builder.and(integrationHistory.jobType.stringValue().contains(condition.jobType()));
        }

        if (condition.indexInfoId() != null) {
            builder.and(integrationHistory.indexInformation.id.stringValue().contains(String.valueOf(condition.indexInfoId())));
        }

        if (condition.baseDateFrom() != null) {
            builder.and(integrationHistory.targetDate.goe(condition.baseDateFrom())); // >=
        }
        if (condition.baseDateTo() != null) {
            builder.and(integrationHistory.targetDate.loe(condition.baseDateTo())); // <=
        }

        if (condition.worker() != null) {
            builder.and(integrationHistory.worker.contains(condition.worker()));
        }

        if (condition.jobTimeFrom() != null) {
            LocalDate localDate = condition.jobTimeFrom().toLocalDate();
            builder.and(integrationHistory.targetDate.goe(localDate)); // >=
        }
        if (condition.jobTimeTo() != null) {
            LocalDate localDate = condition.jobTimeTo().toLocalDate();
            builder.and(integrationHistory.targetDate.loe(localDate)); // <=
        }

        if (condition.status() != null) {
            builder.and(integrationHistory.result.stringValue().contains(condition.status()));
        }

        return builder;
    }

    // 컬럼 정렬
    private BooleanExpression cursorCondition(IntegrationHistorySearchCondition condition) {
        if (condition.cursor() == null) {
            return null;
        }

        QIntegrationHistory integrationHistory = QIntegrationHistory.integrationHistory;
        boolean isDesc = "DESC".equalsIgnoreCase(condition.sortDirection());

        return switch (condition.sortField()) {
            case "targetDate" -> {
                if (condition.cursor().contains("null")) {
                    yield isDesc ? integrationHistory.id.lt(condition.idAfter()) : integrationHistory.id.gt(condition.idAfter());
                }

                LocalDateTime localDateTime = LocalDateTime.parse(condition.cursor(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                LocalDate localDate = localDateTime.toLocalDate();

                yield isDesc
                        ? integrationHistory.targetDate.lt(localDate)
                                .or(integrationHistory.targetDate.eq(localDate).and(integrationHistory.id.lt(condition.idAfter())))
                        : integrationHistory.targetDate.gt(localDate)
                                .or(integrationHistory.targetDate.eq(localDate).and(integrationHistory.id.gt(condition.idAfter())));
            }
            case "jobTime" -> isDesc
                    ? integrationHistory.jobTime.lt(LocalDateTime.parse(condition.cursor())).or(
                            integrationHistory.jobTime.eq(LocalDateTime.parse(condition.cursor())).and(integrationHistory.id.lt(condition.idAfter())))
                    : integrationHistory.jobTime.gt(LocalDateTime.parse(condition.cursor())).or(integrationHistory.jobTime
                            .eq(LocalDateTime.parse(condition.cursor())).and(integrationHistory.id.gt(condition.idAfter())));
            default -> isDesc ? integrationHistory.id.lt(condition.idAfter()) : integrationHistory.id.gt(condition.idAfter());
        };
    }


    // 정렬
    private OrderSpecifier<?>[] orderSpecifiers(IntegrationHistorySearchCondition condition) {
        ComparableExpressionBase<?> target = getSortTarget(condition.sortField());
        Order direction = "DESC".equalsIgnoreCase(condition.sortDirection()) ? Order.DESC : Order.ASC;
        QIntegrationHistory integrationHistory = QIntegrationHistory.integrationHistory;

        return new OrderSpecifier<?>[] {new OrderSpecifier<>(direction, target), new OrderSpecifier<>(direction, integrationHistory.id)};
    }

    // 정렬 타겟 필드 가져오기
    private ComparableExpressionBase<?> getSortTarget(String sortField) {
        QIntegrationHistory integrationHistory = QIntegrationHistory.integrationHistory;
        if (sortField.equals("targetDate")) {
            return integrationHistory.targetDate;
        }
        return integrationHistory.jobTime;
    }
}
