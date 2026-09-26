package com.eunsoly.findex.domain.service.index;

import java.math.BigDecimal;

public record ChartData(String date, BigDecimal value) {

    public static ChartData of(String date, BigDecimal value) {
        return new ChartData(date, value);
    }
}
