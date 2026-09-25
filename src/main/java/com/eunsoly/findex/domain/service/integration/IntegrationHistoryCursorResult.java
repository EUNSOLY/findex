package com.eunsoly.findex.domain.service.integration;

import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.domain.entity.integration.IntegrationHistory;
import java.util.List;

public record IntegrationHistoryCursorResult(List<IntegrationHistory> histories, CursorPaginationResult cursorPaginationResult) {
}
