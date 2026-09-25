package com.eunsoly.findex.controller.integration.dto;

import com.eunsoly.findex.application.integration.dto.SyncIndexResult;

public record SyncIndexInfoResponse(
        Long id,
        String jobType,
        Long indexInfoId,
        String targetDate,
        String worker,
        String jobTime,
        String result) {

    public static SyncIndexInfoResponse of(SyncIndexResult syncIndexInfoResult) {
        return new SyncIndexInfoResponse(
                syncIndexInfoResult.id(),
                syncIndexInfoResult.jobType(),
                syncIndexInfoResult.indexInfoId(),
                syncIndexInfoResult.targetDate(),
                syncIndexInfoResult.worker(),
                syncIndexInfoResult.jobTime(),
                syncIndexInfoResult.result());
    }
}
