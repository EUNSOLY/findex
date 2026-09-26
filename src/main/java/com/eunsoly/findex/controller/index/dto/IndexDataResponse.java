package com.eunsoly.findex.controller.index.dto;

import com.eunsoly.findex.application.index.dto.IndexDataContent;

import java.math.BigDecimal;

public record IndexDataResponse(Long id, Long indexInfoId, String baseDate, String sourceType, BigDecimal marketPrice, BigDecimal closingPrice,
        BigDecimal highPrice, BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice,
        Long marketTotalAmount) {

    public static IndexDataResponse of(IndexDataContent indexData) {
        return new IndexDataResponse(indexData.id(), indexData.indexInfoId(), indexData.baseDate(), indexData.sourceType(), indexData.marketPrice(),
                indexData.closingPrice(), indexData.highPrice(), indexData.lowPrice(), indexData.versus(), indexData.fluctuationRate(),
                indexData.tradingQuantity(), indexData.tradingPrice(), indexData.marketTotalAmount());

    }
}
