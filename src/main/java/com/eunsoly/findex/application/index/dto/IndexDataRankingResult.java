package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.domain.service.index.Performance;

public record IndexDataRankingResult(Performance performance, int rank) {
    public static IndexDataRankingResult of(Performance performance, int rank) {
        return new IndexDataRankingResult(performance, rank);
    }
}
