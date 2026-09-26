package com.eunsoly.findex.controller.index.dto;

import com.eunsoly.findex.application.index.dto.CreateIndexDataCommand;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateIndexDataRequest(Long indexInfoId, LocalDate baseDate, BigDecimal marketPrice, BigDecimal closingPrice, BigDecimal highPrice,
        BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) {

    public CreateIndexDataCommand toCommand() {
        return CreateIndexDataCommand.of(this.indexInfoId, this.baseDate, this.marketPrice, this.closingPrice, this.highPrice, this.lowPrice,
                this.versus, this.fluctuationRate, this.tradingQuantity, this.tradingPrice, this.marketTotalAmount);
    }
}
