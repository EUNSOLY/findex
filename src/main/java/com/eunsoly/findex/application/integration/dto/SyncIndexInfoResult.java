package com.eunsoly.findex.application.integration.dto;

import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record SyncIndexInfoResult(
        Long id,
        String jobType,
        Long indexInfoId,
        String targetDate,
        String worker,
        String jobTime,
        String result) {

    public static SyncIndexInfoResult of(
            IntegrationHistory integrationHistory, IndexInformation indexInformation) {
        Long indexInformationId =
                Optional.ofNullable(indexInformation).map(IndexInformation::getId).orElse(null);

        String targetDate =
                Optional.ofNullable(integrationHistory.getJobTime())
                        .map(date -> date.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                        .orElse(null);

        String jobTime =
                Optional.ofNullable(integrationHistory.getJobTime())
                        .map(
                                time ->
                                        time.format(
                                                DateTimeFormatter.ofPattern(
                                                        "yyyy-MM-dd'T'HH:mm:ss")))
                        .orElse(null);

        return new SyncIndexInfoResult(
                integrationHistory.getId(),
                integrationHistory.getJobType().getValue(),
                indexInformationId,
                targetDate,
                integrationHistory.getWorker(),
                jobTime,
                integrationHistory.getResult().getValue());
    }
}
