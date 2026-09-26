package com.eunsoly.findex.application.index;

import java.time.LocalDate;

public record IndexDataCommand(Long indexInfoId, LocalDate startDate, LocalDate endDate) {
    public static IndexDataCommand of(Long indexInfoId, LocalDate startDate, LocalDate endDate) {

        return new IndexDataCommand(indexInfoId, startDate, endDate);
    }
}
