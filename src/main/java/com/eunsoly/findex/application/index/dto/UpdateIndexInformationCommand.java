package com.eunsoly.findex.application.index.dto;

import java.time.LocalDate;

public record UpdateIndexInformationCommand(Long id, Integer employedItemsCount, LocalDate basePointInTime, Float baseIndex, Boolean favorite) {
    public static UpdateIndexInformationCommand of(Long id, Integer employedItemsCount, LocalDate basePointInTime, Float baseIndex,
            Boolean favorite) {
        return new UpdateIndexInformationCommand(id, employedItemsCount, basePointInTime, baseIndex, favorite);
    }
}
