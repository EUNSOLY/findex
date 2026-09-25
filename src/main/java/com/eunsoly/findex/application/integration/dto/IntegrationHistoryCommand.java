package com.eunsoly.findex.application.integration.dto;

import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import com.eunsoly.findex.domain.entity.integration.JobType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IntegrationHistoryCommand(IndexInformation indexInformation, LocalDate targetDate, String worker, LocalDateTime jobTime) {
    public static IntegrationHistoryCommand of(IndexInformation indexInformation, LocalDate targetDate, String worker, LocalDateTime jobTime) {
        return new IntegrationHistoryCommand(indexInformation, targetDate, worker, jobTime);
    }

    public IntegrationHistory toEntity(String type, boolean success) {
        JobType jobType = JobType.of(type);

        return switch (jobType) {
            case INDEX_INFO -> success ? IntegrationHistory.createSuccessByIndexInfo(indexInformation, targetDate, worker, jobTime)
                    : IntegrationHistory.createFailedByIndexInfo(indexInformation, targetDate, worker, jobTime);
            case INDEX_DATA -> success ? IntegrationHistory.createSuccessByIndexData(indexInformation, targetDate, worker, jobTime)
                    : IntegrationHistory.createFailedByIndexData(indexInformation, targetDate, worker, jobTime);
        };
    }
}
