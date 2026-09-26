package com.eunsoly.findex.application.index;

import com.eunsoly.findex.application.index.dto.CreateIndexDataCommand;
import com.eunsoly.findex.application.index.dto.IndexDataContent;
import com.eunsoly.findex.application.index.dto.IndexDataResult;
import com.eunsoly.findex.common.dto.CursorRequest;
import com.eunsoly.findex.domain.entity.index.IndexData;
import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.domain.service.index.IndexDataCursorResult;
import com.eunsoly.findex.domain.service.index.IndexDataService;
import com.eunsoly.findex.domain.service.index.IndexInformationService;
import com.eunsoly.findex.repository.index.IndexDataSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexDataApplication {
    private static final String CREATE_TYPE = "USER";
    private final IndexDataService indexDataService;
    private final IndexInformationService indexInformationService;

    public IndexDataResult getIndexDatas(IndexDataCommand command, CursorRequest cursorPagination) {
        IndexDataSearchCondition condition =
                IndexDataSearchCondition.of(command.indexInfoId(), command.startDate(), command.endDate(), cursorPagination.idAfter(),
                        cursorPagination.cursor(), cursorPagination.sortField(), cursorPagination.sortDirection(), cursorPagination.size());

        IndexDataCursorResult cursorResult = indexDataService.searchIndexData(condition);

        List<IndexDataContent> indexDataContents = cursorResult.indexData().stream().map(IndexDataContent::of).toList();

        return IndexDataResult.of(indexDataContents, cursorResult.cursorPaginationResult());
    }

    @Transactional
    public IndexDataContent create(CreateIndexDataCommand command) {

        IndexInformation indexInformation = indexInformationService.findById(command.indexInfoId());

        IndexData indexData = command.toEntity(indexInformation, CREATE_TYPE);

        IndexData savedIndexData = indexDataService.upsert(indexData);

        return IndexDataContent.of(savedIndexData);
    }

    @Transactional
    public void delete(Long id) {
        indexDataService.deleteById(id);
    }
}
