package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.repository.index.IndexInformationSummary;

public record IndexInfoSummariseResult(Long id, String indexClassification, String indexName) {

    public static IndexInfoSummariseResult of(IndexInformationSummary summary) {
        return new IndexInfoSummariseResult(summary.id(), summary.indexClassification(), summary.indexName());
    }
}
