package com.eunsoly.findex.application.index;

import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.index.dto.IndexInformationResult;
import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.domain.service.index.IndexInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexInformationApplication {
    private final IndexInformationService indexInformationService;

    public IndexInformationResult createIndexInformation(CreateIndexInformationCommand command) {

        IndexInformation indexInformation = command.toEntity("USER");

        IndexInformation savedInformation = indexInformationService.upsert(indexInformation);

        return IndexInformationResult.of(savedInformation);
    }
}
