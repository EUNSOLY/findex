package com.eunsoly.findex.controller.index.dto;

import com.eunsoly.findex.application.index.dto.IndexInformationResult;

import java.time.LocalDate;

public record IndexInformationResponse(Long id, String indexClassification, String indexName, Integer employedItemsCount, LocalDate basePointInTime,
        Float baseIndex, String sourceType, Boolean favorite) {

    public static IndexInformationResponse of(IndexInformationResult result) {
        return new IndexInformationResponse(result.id(), result.indexClassification(), result.indexName(), result.employedItemsCount(),
                result.basePointInTime(), result.baseIndex(), result.sourceType(), result.favorite());
    }
}
