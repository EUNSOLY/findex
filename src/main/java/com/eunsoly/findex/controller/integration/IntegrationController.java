package com.eunsoly.findex.controller.integration;

import com.eunsoly.findex.application.integration.IntegrationApplication;
import com.eunsoly.findex.application.integration.dto.SyncIndexResult;
import com.eunsoly.findex.controller.integration.dto.SyncIndexDataRequest;
import com.eunsoly.findex.controller.integration.dto.SyncIndexInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IntegrationController {
    private final IntegrationApplication integrationApplication;

    @PostMapping(value = "/api/sync-jobs/index-infos")
    public List<SyncIndexInfoResponse> syncIndexInfos(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        List<SyncIndexResult> results = integrationApplication.syncIndexInfos(clientIp);

        return results.stream().map(SyncIndexInfoResponse::of).toList();
    }

    @PostMapping(value = "/api/sync-jobs/index-data")
    public List<SyncIndexInfoResponse> syncIndexData(
            @RequestBody SyncIndexDataRequest request, HttpServletRequest httpServletRequest) {
        String clientIp = httpServletRequest.getRemoteAddr();
        List<SyncIndexResult> results =
                integrationApplication.syncIndexData(request.toCommand(), clientIp);

        return results.stream().map(SyncIndexInfoResponse::of).toList();
    }
}
