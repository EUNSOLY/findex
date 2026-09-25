package com.eunsoly.findex.controller.integration;

import com.eunsoly.findex.application.integration.IntegrationApplication;
import com.eunsoly.findex.application.integration.dto.SyncIndexResult;
import com.eunsoly.findex.application.integration.dto.SyncJobResult;
import com.eunsoly.findex.application.integration.dto.SyncJobsCommand;
import com.eunsoly.findex.common.dto.CursorPaginationResponse;
import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.common.dto.CursorRequest;
import com.eunsoly.findex.controller.integration.dto.SyncIndexDataRequest;
import com.eunsoly.findex.controller.integration.dto.SyncIndexInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IntegrationController {
    private final IntegrationApplication integrationApplication;

    @PostMapping(value = "/api/sync-jobs/index-infos")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<SyncIndexInfoResponse> syncIndexInfos(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        List<SyncIndexResult> results = integrationApplication.syncIndexInfos(clientIp);

        return results.stream().map(SyncIndexInfoResponse::of).toList();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/api/sync-jobs/index-data")
    public List<SyncIndexInfoResponse> syncIndexData(
            @RequestBody SyncIndexDataRequest request, HttpServletRequest httpServletRequest) {
        String clientIp = httpServletRequest.getRemoteAddr();
        List<SyncIndexResult> results =
                integrationApplication.syncIndexData(request.toCommand(), clientIp);

        return results.stream().map(SyncIndexInfoResponse::of).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/sync-jobs")
    public CursorPaginationResponse<SyncIndexResult> getSyncJobs(
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) Long indexInfoId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd")
                    LocalDate baseDateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd")
                    LocalDate baseDateTo,
            @RequestParam(required = false) String worker,
            @RequestParam(required = false) LocalDateTime jobTimeFrom,
            @RequestParam(required = false) LocalDateTime jobTimeTo,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long idAfter,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "jobTime") String sortField,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        SyncJobsCommand syncJobsCommand =
                SyncJobsCommand.of(
                        jobType,
                        indexInfoId,
                        baseDateFrom,
                        baseDateTo,
                        worker,
                        jobTimeFrom,
                        jobTimeTo,
                        status);
        CursorRequest cursorPaginationCommand =
                CursorRequest.of(idAfter, cursor, sortField, sortDirection, size);

        SyncJobResult syncJobResult =
                integrationApplication.getSyncJobs(syncJobsCommand, cursorPaginationCommand);
        List<SyncIndexResult> content = syncJobResult.content();
        CursorPaginationResult cursorResult = syncJobResult.cursorPaginationResult();

        return CursorPaginationResponse.of(
                content,
                cursorResult.nextCursor(),
                cursorResult.nextIdAfter(),
                cursorResult.size(),
                cursorResult.totalElements(),
                cursorResult.hasNext());
    }
}
