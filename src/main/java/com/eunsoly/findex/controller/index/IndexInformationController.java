package com.eunsoly.findex.controller.index;

import com.eunsoly.findex.application.index.IndexInformationApplication;
import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.index.dto.IndexInformationResult;
import com.eunsoly.findex.controller.index.dto.CreateIndexInformationRequest;
import com.eunsoly.findex.controller.index.dto.IndexInformationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexInformationController {

    private final IndexInformationApplication indexInformationApplication;

    @PostMapping(value = "/api/index-infos")
    public IndexInformationResponse createIndexInformation(@RequestBody CreateIndexInformationRequest request) {
        CreateIndexInformationCommand command = request.toCommand();
        IndexInformationResult result = indexInformationApplication.createIndexInformation(command);

        return IndexInformationResponse.of(result);
    }
}
