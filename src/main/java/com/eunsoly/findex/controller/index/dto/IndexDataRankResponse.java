package com.eunsoly.findex.controller.index.dto;

import com.eunsoly.findex.application.index.dto.IndexDataRankingResult;
import com.eunsoly.findex.domain.service.index.Performance;

public record IndexDataRankResponse(Performance performance, int rank) {

    public static IndexDataRankResponse of(IndexDataRankingResult result) {
        return new IndexDataRankResponse(result.performance(), result.rank());
    }
}
