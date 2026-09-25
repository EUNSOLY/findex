package com.eunsoly.findex.application.index.dto;

import com.eunsoly.findex.common.dto.CursorPaginationResult;

import java.util.List;

public record IndexInformationListResult(List<IndexInformationResult> content, CursorPaginationResult cursorPaginationResult) {

    public static IndexInformationListResult of(List<IndexInformationResult> content, CursorPaginationResult cursorPaginationResult) {
        return new IndexInformationListResult(content, cursorPaginationResult);
    }

}
