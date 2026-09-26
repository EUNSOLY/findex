package com.eunsoly.findex.repository.index;

import java.time.LocalDate;

public record IndexDataSearchCondition(Long indexInfoId, LocalDate startDate, LocalDate endDate, Long idAfter, String cursor, String sortField,
        String sortDirection, Integer size) {
    public static IndexDataSearchCondition of(Long indexInfoId, LocalDate startDate, LocalDate endDate, Long idAfter, String cursor, String sortField,
            String sortDirection, Integer size) {
        return new IndexDataSearchCondition(indexInfoId, startDate, endDate, idAfter, cursor, sortField, sortDirection, size);

    }
}
