package com.eunsoly.findex.application.integration.dto;

import com.eunsoly.findex.common.dto.CursorPaginationResult;
import java.util.List;

public record SyncJobResult(
        List<SyncIndexResult> content, CursorPaginationResult cursorPaginationResult) {
    public static SyncJobResult of(
            List<SyncIndexResult> content, CursorPaginationResult cursorPaginationResult) {
        return new SyncJobResult(content, cursorPaginationResult);
    }
}
