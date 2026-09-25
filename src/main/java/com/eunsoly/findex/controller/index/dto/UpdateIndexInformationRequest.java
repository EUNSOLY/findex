package com.eunsoly.findex.controller.index.dto;

import com.eunsoly.findex.application.index.dto.UpdateIndexInformationCommand;

import java.time.LocalDate;

public record UpdateIndexInformationRequest(Integer employedItemsCount, LocalDate basePointInTime, Float baseIndex, Boolean favorite) {

    public UpdateIndexInformationCommand toCommand(Long id) {
        return UpdateIndexInformationCommand.of(id, this.employedItemsCount, this.basePointInTime, this.baseIndex, this.favorite);
    }
}
