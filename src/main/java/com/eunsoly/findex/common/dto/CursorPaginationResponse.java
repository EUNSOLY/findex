package com.eunsoly.findex.common.dto;

import java.util.List;

public record CursorPaginationResponse<T>(List<T> content, String nextCursor, Long nextIdAfter, Integer size, Long totalElements, boolean hasNext) {

    public static <T> CursorPaginationResponse<T> of(List<T> content, String nextCursor, Long nextIdAfter, Integer size, Long totalElements,
            boolean hasNext) {
        return new CursorPaginationResponse<>(content, nextCursor, nextIdAfter, size, totalElements, hasNext);
    }
}
