package com.eunsoly.findex.controller.index.dto;

import com.eunsoly.findex.application.index.dto.IndexInfoSummariseResult;

public record IndexInfoSummariseResponse(Long id, String indexClassification, String indexName) {

    public static IndexInfoSummariseResponse of(IndexInfoSummariseResult result) {
        return new IndexInfoSummariseResponse(result.id(), result.indexClassification(), result.indexName());
    }
}
