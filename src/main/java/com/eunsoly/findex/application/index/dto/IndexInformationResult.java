package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.domain.entity.index.IndexInformation;

import java.time.LocalDate;

public record IndexInformationResult(Long id, String indexClassification, String indexName, Integer employedItemsCount, LocalDate basePointInTime,
        Float baseIndex, Boolean favorite) {

    public static IndexInformationResult of(IndexInformation indexInformation) {
        return new IndexInformationResult(indexInformation.getId(), indexInformation.getIndexClassification(), indexInformation.getIndexName(),
                indexInformation.getEmployedItemsCount(), indexInformation.getBasePointInTime(), indexInformation.getBaseIndex(),
                indexInformation.getFavorite());
    }
}
