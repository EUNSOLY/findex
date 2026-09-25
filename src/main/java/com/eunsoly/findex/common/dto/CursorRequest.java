package com.eunsoly.findex.common.dto;

public record CursorRequest(
        Long idAfter, String cursor, String sortField, String sortDirection, Integer size) {
    public static CursorRequest of(
            Long idAfter, String cursor, String sortField, String sortDirection, Integer size) {
        return new CursorRequest(idAfter, cursor, sortField, sortDirection, size);
    }
}
