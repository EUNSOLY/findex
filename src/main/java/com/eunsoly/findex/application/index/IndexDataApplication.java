package com.eunsoly.findex.application.index;

import com.eunsoly.findex.application.index.dto.IndexDataContent;
import com.eunsoly.findex.application.index.dto.IndexDataResult;
import com.eunsoly.findex.common.dto.CursorRequest;
import com.eunsoly.findex.domain.service.index.IndexDataCursorResult;
import com.eunsoly.findex.domain.service.index.IndexDataService;
import com.eunsoly.findex.repository.index.IndexDataSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexDataApplication {
    private final IndexDataService indexDataService;

    public IndexDataResult getIndexDatas(IndexDataCommand command, CursorRequest cursorPagination) {
        IndexDataSearchCondition condition =
                IndexDataSearchCondition.of(command.indexInfoId(), command.startDate(), command.endDate(), cursorPagination.idAfter(),
                        cursorPagination.cursor(), cursorPagination.sortField(), cursorPagination.sortDirection(), cursorPagination.size());

        IndexDataCursorResult cursorResult = indexDataService.searchIndexData(condition);

        List<IndexDataContent> indexDataContents = cursorResult.indexData().stream().map(IndexDataContent::of).toList();

        return IndexDataResult.of(indexDataContents, cursorResult.cursorPaginationResult());
    }
}
