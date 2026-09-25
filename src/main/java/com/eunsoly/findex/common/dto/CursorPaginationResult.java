package com.eunsoly.findex.common.dto;

public record CursorPaginationResult(String nextCursor, Long nextIdAfter, Integer size, Long totalElements, Boolean hasNext) {

    public static CursorPaginationResult of(String nextCursor, Long nextIdAfter, Integer size, Long totalElements, boolean hasNext) {
        return new CursorPaginationResult(nextCursor, nextIdAfter, size, totalElements, hasNext);
    }
}
