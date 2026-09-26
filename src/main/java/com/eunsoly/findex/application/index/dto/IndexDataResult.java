package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.common.dto.CursorPaginationResult;

import java.util.List;

public record IndexDataResult(List<IndexDataContent> content, CursorPaginationResult cursorPaginationResult) {

    public static IndexDataResult of(List<IndexDataContent> content, CursorPaginationResult cursor) {
        return new IndexDataResult(content, cursor);
    }
}
