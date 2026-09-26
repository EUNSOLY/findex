package com.eunsoly.findex.application.index.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateIndexDataCommand(Long id, LocalDate baseDate, BigDecimal marketPrice, BigDecimal closingPrice, BigDecimal highPrice,
        BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) {

    public static UpdateIndexDataCommand of(Long id, LocalDate baseDate, BigDecimal marketPrice, BigDecimal closingPrice, BigDecimal highPrice,
            BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) {
        return new UpdateIndexDataCommand(id, baseDate, marketPrice, closingPrice, highPrice, lowPrice, versus, fluctuationRate, tradingQuantity,
                tradingPrice, marketTotalAmount);
    }
}
