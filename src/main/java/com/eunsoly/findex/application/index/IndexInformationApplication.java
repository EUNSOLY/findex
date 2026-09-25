package com.eunsoly.findex.application.index;

import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.index.dto.IndexInformationResult;
import com.eunsoly.findex.application.index.dto.UpdateIndexInformationCommand;
import com.eunsoly.findex.domain.entity.index.IndexInformation;
import com.eunsoly.findex.domain.service.index.IndexInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IndexInformationApplication {
    private final IndexInformationService indexInformationService;

    public IndexInformationResult createIndexInformation(CreateIndexInformationCommand command) {

        IndexInformation indexInformation = command.toEntity("USER");

        IndexInformation savedInformation = indexInformationService.upsert(indexInformation);

        return IndexInformationResult.of(savedInformation);
    }

    @Transactional
    public IndexInformationResult updateIndexInformation(UpdateIndexInformationCommand command) {

        IndexInformation updatedIndexInformation = indexInformationService.findById(command.id());

        updatedIndexInformation.updateByUser(command.employedItemsCount(), command.basePointInTime(), command.baseIndex(), command.favorite());

        return IndexInformationResult.of(updatedIndexInformation);
    }

    public IndexInformationResult getIndexInformation(Long id) {
        IndexInformation indexInformation = indexInformationService.findById(id);

        return IndexInformationResult.of(indexInformation);

    }

    public void deleteInformation(Long id) {
        indexInformationService.deleteInformation(id);
    }
}
