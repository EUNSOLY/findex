package com.eunsoly.findex.controller.index.dto;

import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;

import java.time.LocalDate;

public record CreateIndexInformationRequest(String indexClassification, String indexName, Integer employedItemsCount, LocalDate basePointInTime,
        Float baseIndex, Boolean favorite) {


    public CreateIndexInformationCommand toCommand() {
        return CreateIndexInformationCommand.of(indexClassification, indexName, employedItemsCount, basePointInTime, baseIndex, favorite);
    }
}
