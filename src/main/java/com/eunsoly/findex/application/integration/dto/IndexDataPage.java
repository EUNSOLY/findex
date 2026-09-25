package com.eunsoly.findex.application.integration.dto;

import com.eunsoly.findex.application.index.dto.CreateIndexDataCommand;
import java.util.List;

public record IndexDataPage(List<CreateIndexDataCommand> items, boolean hasNext) {

    public static IndexDataPage of(List<CreateIndexDataCommand> commands, boolean hasNext) {
        return new IndexDataPage(commands, hasNext);
    }
}
