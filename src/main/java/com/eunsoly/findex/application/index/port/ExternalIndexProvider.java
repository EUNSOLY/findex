package com.eunsoly.findex.application.index.port;

import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.integration.dto.IndexDataPage;
import java.util.List;

public interface ExternalIndexProvider {

    List<CreateIndexInformationCommand> getOpenApiIndexInfos();

    IndexDataPage getOpenApiIndexData(
            int pageNo, String indexClassification, String indexName, String from, String to);
}
