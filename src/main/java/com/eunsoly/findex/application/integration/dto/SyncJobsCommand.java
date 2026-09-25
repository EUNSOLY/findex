package com.eunsoly.findex.application.integration.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SyncJobsCommand(String jobType, Long indexInfoId, LocalDate baseDateFrom, LocalDate baseDateTo, String worker,
        LocalDateTime jobTimeFrom, LocalDateTime jobTimeTo, String status) {

    public static SyncJobsCommand of(String jobType, Long indexInfoId, LocalDate baseDateFrom, LocalDate baseDateTo, String worker,
            LocalDateTime jobTimeFrom, LocalDateTime jobTimeTo, String status) {
        return new SyncJobsCommand(jobType, indexInfoId, baseDateFrom, baseDateTo, worker, jobTimeFrom, jobTimeTo, status);
    }
}
