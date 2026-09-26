package com.eunsoly.findex.controller.index;

import com.eunsoly.findex.application.index.IndexDataApplication;
import com.eunsoly.findex.application.index.IndexDataCommand;
import com.eunsoly.findex.application.index.dto.CreateIndexDataCommand;
import com.eunsoly.findex.application.index.dto.IndexDataContent;
import com.eunsoly.findex.application.index.dto.IndexDataResult;
import com.eunsoly.findex.common.dto.CursorPaginationResponse;
import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.common.dto.CursorRequest;
import com.eunsoly.findex.controller.index.dto.CreateIndexDataRequest;
import com.eunsoly.findex.controller.index.dto.IndexDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class IndexDataController {
    private final IndexDataApplication indexDataApplication;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/index-data")
    public CursorPaginationResponse<IndexDataContent> getIndexData(@RequestParam(required = false) Long indexInfoId,
            @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long idAfter, @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "indexClassification") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        IndexDataCommand command = IndexDataCommand.of(indexInfoId, startDate, endDate);
        CursorRequest cursorPagination = CursorRequest.of(idAfter, cursor, sortField, sortDirection, size);


        IndexDataResult result = indexDataApplication.getIndexDatas(command, cursorPagination);
        List<IndexDataContent> contents = result.content();
        CursorPaginationResult cursorResult = result.cursorPaginationResult();

        return CursorPaginationResponse.of(contents, cursorResult.nextCursor(), cursorResult.nextIdAfter(), cursorResult.size(),
                cursorResult.totalElements(), cursorResult.hasNext());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/index-data")
    public IndexDataResponse createIndexData(@RequestBody CreateIndexDataRequest request) {
        CreateIndexDataCommand command = request.toCommand();

        IndexDataContent result = indexDataApplication.create(command);

        return IndexDataResponse.of(result);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/api/index-data/{id}")
    public void deleteIndexData(@PathVariable Long id) {
        indexDataApplication.delete(id);
    }
}
