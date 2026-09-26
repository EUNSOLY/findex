package com.eunsoly.findex.domain.entity.index;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum PeriodType {
    DAILY("DAILY"), //
    WEEKLY("WEEKLY"), // 주간
    MONTHLY("MONTHLY"), // 한달
    QUARTERLY("QUARTERLY"), // 세달
    YEARLY("YEARLY"); // 일년

    String value;

    public static PeriodType of(String type) {
        for (PeriodType periodType : PeriodType.values()) {
            if (periodType.getValue().equals(type)) {
                return periodType;
            }
        }
        // TODO : 커스텀 예외 생성하기
        throw new RuntimeException("");
    }
}
