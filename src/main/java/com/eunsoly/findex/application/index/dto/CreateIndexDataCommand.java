package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.domain.entity.SourceType;
import com.eunsoly.findex.domain.entity.index.IndexData;
import com.eunsoly.findex.domain.entity.index.IndexInformation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateIndexDataCommand(Long indexInfoId, LocalDate baseDate, BigDecimal marketPrice, BigDecimal closingPrice, BigDecimal highPrice,
        BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) {

    public static CreateIndexDataCommand of(Long indexInfoId, LocalDate baseDate, BigDecimal marketPrice, BigDecimal closingPrice,
            BigDecimal highPrice, BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice,
            Long marketTotalAmount) {
        return new CreateIndexDataCommand(indexInfoId, baseDate, marketPrice, closingPrice, highPrice, lowPrice, versus, fluctuationRate,
                tradingQuantity, tradingPrice, marketTotalAmount);
    }

    public IndexData toEntity(IndexInformation indexInformation, String type) {
        SourceType sourceType = SourceType.of(type);

        if (sourceType == SourceType.USER) {
            return IndexData.createByUser(indexInformation, this.baseDate, this.marketPrice, this.closingPrice, this.highPrice, this.lowPrice,
                    this.versus, this.fluctuationRate, this.tradingQuantity, this.tradingPrice, this.marketTotalAmount);
        }

        return IndexData.createByIntegration(indexInformation, this.baseDate, this.marketPrice, this.closingPrice, this.highPrice, this.lowPrice,
                this.versus, this.fluctuationRate, this.tradingQuantity, this.tradingPrice, this.marketTotalAmount);
    }
}
