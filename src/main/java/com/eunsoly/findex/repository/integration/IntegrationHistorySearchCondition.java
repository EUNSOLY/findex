package com.eunsoly.findex.repository.integration;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record IntegrationHistorySearchCondition(
        // 필터
        String jobType, Long indexInfoId, LocalDate baseDateFrom, LocalDate baseDateTo, String worker, LocalDateTime jobTimeFrom,
        LocalDateTime jobTimeTo, String status,

        // 커서 페이지네이션
        Long idAfter, String cursor, String sortField, String sortDirection, Integer size) {

    public static IntegrationHistorySearchCondition of(String jobType, Long indexInfoId, LocalDate baseDateFrom, LocalDate baseDateTo, String worker,
            LocalDateTime jobTimeFrom, LocalDateTime jobTimeTo, String status,
            // 커서 페이지네이션
            Long idAfter, String cursor, String sortField, String sortDirection, Integer size) {
        return new IntegrationHistorySearchCondition(jobType, indexInfoId, baseDateFrom, baseDateTo, worker, jobTimeFrom, jobTimeTo, status, idAfter,
                cursor, sortField, sortDirection, size);
    }
}
