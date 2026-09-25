package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.domain.entity.SourceType;
import com.eunsoly.findex.domain.entity.index.IndexInformation;

import java.time.LocalDate;

public record CreateIndexInformationCommand(String indexClassification, String indexName, Integer employedItemsCount, LocalDate basePointInTime,
        Float baseIndex, Boolean favorite) {

    public static CreateIndexInformationCommand of(String indexClassification, String indexName, Integer employedItemsCount,
            LocalDate basePointInTime, Float baseIndex, Boolean favorite) {
        return new CreateIndexInformationCommand(indexClassification, indexName, employedItemsCount, basePointInTime, baseIndex, favorite);
    }

    public IndexInformation toEntity(String type) {
        SourceType sourceType = SourceType.of(type);

        if (sourceType == SourceType.USER) {
            return IndexInformation.createByUser(this.indexClassification, this.indexName, this.employedItemsCount, this.basePointInTime,
                    this.baseIndex, this.favorite);
        }

        return IndexInformation.createByIntegration(this.indexClassification, this.indexName, this.employedItemsCount, this.basePointInTime,
                this.baseIndex);
    }
}
