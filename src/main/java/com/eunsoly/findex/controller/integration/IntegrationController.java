package com.eunsoly.findex.controller.integration;

import com.eunsoly.findex.application.integration.IntegrationApplication;
import com.eunsoly.findex.application.integration.dto.SyncIndexInfoResult;
import com.eunsoly.findex.controller.integration.dto.SyncIndexInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IntegrationController {
    private final IntegrationApplication integrationApplication;

    @PostMapping(value = "/api/sync-jobs/index-infos")
    public List<SyncIndexInfoResponse> syncIndexInfos(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        List<SyncIndexInfoResult> results = integrationApplication.syncIndexInfos(clientIp);

        return results.stream().map(SyncIndexInfoResponse::of).toList();
    }
}
