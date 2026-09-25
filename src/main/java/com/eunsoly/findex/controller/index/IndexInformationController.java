package com.eunsoly.findex.controller.index;

import com.eunsoly.findex.application.index.IndexInformationApplication;
import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.index.dto.IndexInfoSummariseResult;
import com.eunsoly.findex.application.index.dto.IndexInformationResult;
import com.eunsoly.findex.application.index.dto.UpdateIndexInformationCommand;
import com.eunsoly.findex.controller.index.dto.CreateIndexInformationRequest;
import com.eunsoly.findex.controller.index.dto.IndexInfoSummariseResponse;
import com.eunsoly.findex.controller.index.dto.IndexInformationResponse;
import com.eunsoly.findex.controller.index.dto.UpdateIndexInformationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IndexInformationController {

    private final IndexInformationApplication indexInformationApplication;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/index-infos")
    public IndexInformationResponse createIndexInformation(@RequestBody CreateIndexInformationRequest request) {
        CreateIndexInformationCommand command = request.toCommand();
        IndexInformationResult result = indexInformationApplication.createIndexInformation(command);

        return IndexInformationResponse.of(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/api/index-infos/{id}")
    public IndexInformationResponse updateIndexInformation(@RequestBody UpdateIndexInformationRequest request, @PathVariable Long id) {
        UpdateIndexInformationCommand command = request.toCommand(id);

        IndexInformationResult result = indexInformationApplication.updateIndexInformation(command);

        return IndexInformationResponse.of(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/index-infos/{id}")
    public IndexInformationResponse getIndexInformation(@PathVariable Long id) {
        IndexInformationResult result = indexInformationApplication.getIndexInformation(id);
        return IndexInformationResponse.of(result);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/api/index-infos/{id}")
    public void deleteIndexInformation(@PathVariable Long id) {
        indexInformationApplication.deleteInformation(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/index-infos/summaries")
    public List<IndexInfoSummariseResponse> getSummaries() {
        List<IndexInfoSummariseResult> results = indexInformationApplication.getSummarise();
        return results.stream().map(IndexInfoSummariseResponse::of).toList();
    }
}
