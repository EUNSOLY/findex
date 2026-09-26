package com.eunsoly.findex.domain.service.index;

import java.util.List;

public record IndexChartDataResult(List<ChartData> dataPoints, List<ChartData> ma5DataPoints, List<ChartData> ma20DataPoints) {
    public static IndexChartDataResult of(List<ChartData> dataPoints, List<ChartData> ma5DataPoints, List<ChartData> ma20DataPoints) {
        return new IndexChartDataResult(dataPoints, ma5DataPoints, ma20DataPoints);
    }
}
