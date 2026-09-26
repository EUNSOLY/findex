package com.eunsoly.findex.domain.entity.index;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"index_information_id", "base_date"}))
public class IndexData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 기본 EAGER라서 LAZY로 변경
    @JoinColumn(name = "index_information_id", nullable = false) // FK 매핑할 컬럼명 지정
    private IndexInformation indexInformation;

    @Column(nullable = false)
    private LocalDate baseDate; // 기준일자

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SourceType sourceType;

    @Column(nullable = false)
    private BigDecimal marketPrice; // 시가

    @Column(nullable = false)
    private BigDecimal closingPrice; // 종가

    @Column(nullable = false)
    private BigDecimal highPrice; // 고가

    @Column(nullable = false)
    private BigDecimal lowPrice; // 저가

    @Column(nullable = false)
    private BigDecimal versus; // 대비

    @Column(nullable = false)
    private BigDecimal fluctuationRate; // 등락률

    @Column(nullable = false)
    private Long tradingQuantity; // 거래량

    @Column(nullable = false)
    private Long tradingPrice; // 거래대금

    @Column(nullable = false)
    private Long marketTotalAmount; // 시가 총액

    private static IndexData baseCreate(IndexInformation indexInformation, LocalDate baseDate, BigDecimal marketPrice, BigDecimal closingPrice,
            BigDecimal highPrice, BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice,
            Long marketTotalAmount) {
        IndexData indexData = new IndexData();
        indexData.indexInformation = indexInformation;
        indexData.baseDate = baseDate;
        indexData.marketPrice = marketPrice;
        indexData.closingPrice = closingPrice;
        indexData.highPrice = highPrice;
        indexData.lowPrice = lowPrice;
        indexData.versus = versus;
        indexData.fluctuationRate = fluctuationRate;
        indexData.tradingQuantity = tradingQuantity;
        indexData.tradingPrice = tradingPrice;
        indexData.marketTotalAmount = marketTotalAmount;

        return indexData;
    }

    public static IndexData createByIntegration(IndexInformation indexInformation, LocalDate baseDate, BigDecimal marketPrice,
            BigDecimal closingPrice, BigDecimal highPrice, BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity,
            Long tradingPrice, Long marketTotalAmount) {
        IndexData indexData = IndexData.baseCreate(indexInformation, baseDate, marketPrice, closingPrice, highPrice, lowPrice, versus,
                fluctuationRate, tradingQuantity, tradingPrice, marketTotalAmount);
        indexData.sourceType = SourceType.OPEN_API;

        return indexData;
    }

    public static IndexData createByUser(IndexInformation indexInformation, LocalDate baseDate, BigDecimal marketPrice, BigDecimal closingPrice,
            BigDecimal highPrice, BigDecimal lowPrice, BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice,
            Long marketTotalAmount) {
        IndexData indexData = IndexData.baseCreate(indexInformation, baseDate, marketPrice, closingPrice, highPrice, lowPrice, versus,
                fluctuationRate, tradingQuantity, tradingPrice, marketTotalAmount);

        indexData.sourceType = SourceType.USER;

        return indexData;
    }

    public IndexData updateByUser(BigDecimal marketPrice, BigDecimal closingPrice, BigDecimal highPrice, BigDecimal lowPrice, BigDecimal versus,
            BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) {
        this.baseUpdate(marketPrice, closingPrice, highPrice, lowPrice, versus, fluctuationRate, tradingQuantity, tradingPrice, marketTotalAmount);
        this.sourceType = SourceType.USER;
        return this;
    }

    public IndexData updateByIntegration(BigDecimal marketPrice, BigDecimal closingPrice, BigDecimal highPrice, BigDecimal lowPrice,
            BigDecimal versus, BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) {

        this.baseUpdate(marketPrice, closingPrice, highPrice, lowPrice, versus, fluctuationRate, tradingQuantity, tradingPrice, marketTotalAmount);
        this.sourceType = SourceType.OPEN_API;
        return this;
    }

    private void baseUpdate(BigDecimal marketPrice, BigDecimal closingPrice, BigDecimal highPrice, BigDecimal lowPrice, BigDecimal versus,
            BigDecimal fluctuationRate, Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) {
        this.marketPrice = marketPrice;
        this.closingPrice = closingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.versus = versus;
        this.fluctuationRate = fluctuationRate;
        this.tradingQuantity = tradingQuantity;
        this.tradingPrice = tradingPrice;
        this.marketTotalAmount = marketTotalAmount;
    }
}
