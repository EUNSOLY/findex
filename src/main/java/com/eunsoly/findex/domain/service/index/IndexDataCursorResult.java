package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.domain.entity.index.IndexData;

import java.util.List;

public record IndexDataCursorResult(List<IndexData> indexData, CursorPaginationResult cursorPaginationResult) {
}
