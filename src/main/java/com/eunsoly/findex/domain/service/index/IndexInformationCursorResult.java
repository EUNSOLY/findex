package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.domain.entity.index.IndexInformation;

import java.util.List;

public record IndexInformationCursorResult(List<IndexInformation> indexInformations, CursorPaginationResult cursorPaginationResult) {
}
