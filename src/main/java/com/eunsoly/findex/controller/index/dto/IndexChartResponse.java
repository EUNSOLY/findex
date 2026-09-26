package com.eunsoly.findex.controller.index.dto;

import com.eunsoly.findex.application.index.dto.IndexChartResult;
import com.eunsoly.findex.domain.service.index.ChartData;

import java.util.List;

public record IndexChartResponse(Long indexInfoId, String indexClassification, String indexName, String periodType, List<ChartData> dataPoints,
        List<ChartData> ma5DataPoints, List<ChartData> ma20DataPoints) {
    public static IndexChartResponse of(IndexChartResult result) {
        return new IndexChartResponse(result.indexInfoId(), result.indexClassification(), result.indexName(), result.periodType(),
                result.dataPoints(), result.ma5DataPoints(), result.ma20DataPoints());
    }

}
