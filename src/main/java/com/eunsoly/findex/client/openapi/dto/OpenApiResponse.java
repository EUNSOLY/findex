package com.eunsoly.findex.client.openapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OpenApiResponse {

    private Response response;

    // Response 정의
    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;
    }

    // Header를 정의
    @Getter
    @Setter
    public static class Header {
        private String resultCode; // 결과코드
        private String resultMsg; // 결과메시지
    }

    // Body는 배열의 각 항목을 나타냄
    @Getter
    @Setter
    public static class Body {
        @JsonProperty("numOfRows")
        private Long numOfRows; // 한 페이지 결과 수

        @JsonProperty("pageNo")
        private Integer pageNo; // 페이지 번호

        @JsonProperty("totalCount")
        private Integer totalCount; // 전체 결과 수

        @JsonProperty("items")
        private Items items;
    }

    @Getter
    @Setter
    public static class Items {
        @JsonProperty("item")
        private List<Item> items;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {

        @JsonProperty("basPntm")
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate basePointInTime; // 기준시점

        @JsonProperty("basIdx")
        private Float baseIndex; // 기준지수

        @JsonProperty("basDt")
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate baseDate; // 기준일자

        @JsonProperty("idxCsf")
        private String indexClassification; // 지수분류명

        @JsonProperty("idxNm")
        private String indexName; // 지수명

        @JsonProperty("epyItmsCnt")
        private Integer employedItemsCount; // 채용종목수

        @JsonProperty("clpr")
        private BigDecimal closingPrice; // 종가

        @JsonProperty("vs")
        private BigDecimal versus; // 대비

        @JsonProperty("fltRt")
        private BigDecimal fluctuationRate; // 등락률

        @JsonProperty("mkp")
        private BigDecimal marketPrice; // 시가

        @JsonProperty("hipr")
        private BigDecimal highPrice; // 고가

        @JsonProperty("lopr")
        private BigDecimal lowPrice; // 저가

        @JsonProperty("trqu")
        private Long tradingQuantity; // 거래량

        @JsonProperty("trPrc")
        private Long tradingPrice; // 거래대금

        @JsonProperty("lstgMrktTotAmt")
        private Long marketTotalAmount; // 상장시가총액

        // 아래 내용은 실제 미사용(제거예정)
        @JsonProperty("lsYrEdVsFltRt")
        private BigDecimal lsYrEdVsFltRt; // 전년말대비_등락률

        @JsonProperty("lsYrEdVsFltRg")
        private BigDecimal lsYrEdVsFltRg; // 전년말대비_등락폭

        @JsonProperty("yrWRcrdHgst")
        private BigDecimal yrWRcrdHgst; // 연중기록최고

        @JsonProperty("yrWRcrdHgstDt")
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate yrWRcrdHgstDt; // 연중기록최고일자

        @JsonProperty("yrWRcrdLwst")
        private BigDecimal yrWRcrdLwst; // 연중기록최저

        @JsonProperty("yrWRcrdLwstDt")
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate yrWRcrdLwstDt; // 연중기록최저일자
    }

    public Integer getTotalCount() {
        return this.response.getBody().totalCount;
    }

    public Items getItems() {
        return this.response.getBody().getItems();
    }
}
