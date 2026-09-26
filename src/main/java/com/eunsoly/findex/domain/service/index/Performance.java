package com.eunsoly.findex.domain.service.index;

import java.math.BigDecimal;

public record Performance(Long indexInfoId, String indexClassification, String indexName, BigDecimal versus, BigDecimal fluctuationRate,
        BigDecimal currentPrice, BigDecimal beforePrice) {
    public static Performance of(Long indexInfoId, String indexClassification, String indexName, BigDecimal versus, BigDecimal fluctuationRate,
            BigDecimal currentPrice, BigDecimal beforePrice) {
        return new Performance(indexInfoId, indexClassification, indexName, versus, fluctuationRate, currentPrice, beforePrice);
    }
}
