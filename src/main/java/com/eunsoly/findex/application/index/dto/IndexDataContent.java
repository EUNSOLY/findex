package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.domain.entity.index.IndexData;

import java.math.BigDecimal;

public record IndexDataContent(Long id, Long indexInfoId, String baseDate, String sourceType, BigDecimal marketPrice, BigDecimal closingPrice,
        BigDecimal highPrice, BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice,
        Long marketTotalAmount) {

    public static IndexDataContent of(IndexData indexData) {
        return new IndexDataContent(indexData.getId(), indexData.getIndexInformation().getId(), indexData.getBaseDate().toString(),
                indexData.getSourceType().getValue(), indexData.getMarketPrice(), indexData.getClosingPrice(), indexData.getHighPrice(),
                indexData.getLowPrice(), indexData.getVersus(), indexData.getFluctuationRate(), indexData.getTradingQuantity(),
                indexData.getTradingPrice(), indexData.getMarketTotalAmount());
    }
}

