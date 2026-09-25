package com.eunsoly.findex.application.integration.dto;

import java.util.List;

public record SyncIndexDataCommand(List<Long> indexInfoIds, String baseDateFrom, String baseDateTo) {

    public static SyncIndexDataCommand of(List<Long> indexInfoIds, String baseDateFrom, String baseDateTo) {
        return new SyncIndexDataCommand(indexInfoIds, baseDateFrom, baseDateTo);
    }
}
