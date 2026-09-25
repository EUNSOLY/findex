package com.eunsoly.findex.application.integration.port;

import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import java.util.List;

public interface IndexInformationProvider {

    List<CreateIndexInformationCommand> getOpenApiIndexInfos();
}
