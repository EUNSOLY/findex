package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.domain.service.index.ChartData;
import com.eunsoly.findex.domain.service.index.IndexChartDataResult;

import java.util.List;

public record IndexChartResult(Long indexInfoId, String indexClassification, String indexName, String periodType, List<ChartData> dataPoints,
        List<ChartData> ma5DataPoints, List<ChartData> ma20DataPoints) {
    public static IndexChartResult of(Long indexInfoId, String indexClassification, String indexName, String periodType,
            IndexChartDataResult chartDataResult

    ) {
        return new IndexChartResult(indexInfoId, indexClassification, indexName, periodType, chartDataResult.dataPoints(),
                chartDataResult.ma5DataPoints(), chartDataResult.ma20DataPoints());
    }
}
