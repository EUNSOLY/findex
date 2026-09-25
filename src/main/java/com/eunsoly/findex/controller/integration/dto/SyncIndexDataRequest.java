package com.eunsoly.findex.controller.integration.dto;

import com.eunsoly.findex.application.integration.dto.SyncIndexDataCommand;
import java.util.List;

public record SyncIndexDataRequest(
        List<Long> indexInfoIds, String baseDateFrom, String baseDateTo) {

    public SyncIndexDataCommand toCommand() {
        return SyncIndexDataCommand.of(this.indexInfoIds, this.baseDateFrom, this.baseDateTo);
    }
}
