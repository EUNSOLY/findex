package com.eunsoly.findex.application.index.dto;

public record IndexInformationCommand(String indexClassification, String indexName, Boolean favorite) {
    public static IndexInformationCommand of(String indexClassification, String indexName, Boolean favorite) {
        return new IndexInformationCommand(indexClassification, indexName, favorite);
    }
}
