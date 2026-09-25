package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.domain.entity.SourceType;
import com.eunsoly.findex.domain.entity.index.IndexData;
import com.eunsoly.findex.domain.entity.index.IndexInformation;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateIndexDataCommand(
        IndexInformation indexInformation,
        LocalDate baseDate,
        BigDecimal marketPrice,
        BigDecimal closingPrice,
        BigDecimal highPrice,
        BigDecimal lowPrice,
        BigDecimal versus,
        BigDecimal fluctuationRate,
        Long tradingQuantity,
        Long tradingPrice,
        Long marketTotalAmount) {

    public static CreateIndexDataCommand of(
            IndexInformation indexInformation,
            LocalDate baseDate,
            BigDecimal marketPrice,
            BigDecimal closingPrice,
            BigDecimal highPrice,
            BigDecimal lowPrice,
            BigDecimal versus,
            BigDecimal fluctuationRate,
            Long tradingQuantity,
            Long tradingPrice,
            Long marketTotalAmount) {
        return new CreateIndexDataCommand(
                indexInformation,
                baseDate,
                marketPrice,
                closingPrice,
                highPrice,
                lowPrice,
                versus,
                fluctuationRate,
                tradingQuantity,
                tradingPrice,
                marketTotalAmount);
    }

    public IndexData toEntity(String type) {
        SourceType sourceType = SourceType.of(type);

        if (sourceType == SourceType.USER) {
            return IndexData.createByUser(
                    this.indexInformation,
                    this.baseDate,
                    this.marketPrice,
                    this.closingPrice,
                    this.highPrice,
                    this.lowPrice,
                    this.versus,
                    this.fluctuationRate,
                    this.tradingQuantity,
                    this.tradingPrice,
                    this.marketTotalAmount);
        }

        return IndexData.createByIntegration(
                this.indexInformation,
                this.baseDate,
                this.marketPrice,
                this.closingPrice,
                this.highPrice,
                this.lowPrice,
                this.versus,
                this.fluctuationRate,
                this.tradingQuantity,
                this.tradingPrice,
                this.marketTotalAmount);
    }
}
