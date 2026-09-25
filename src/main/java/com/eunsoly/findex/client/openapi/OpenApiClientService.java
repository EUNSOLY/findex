package com.eunsoly.findex.client.openapi;

import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.integration.port.IndexInformationProvider;
import com.eunsoly.findex.client.openapi.dto.OpenApiResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenApiClientService implements IndexInformationProvider {
    private final OpenApiClient openApiClient;

    @Override
    public List<CreateIndexInformationCommand> getOpenApiIndexInfos() {
        List<CreateIndexInformationCommand> result = new ArrayList<>();
        int pageNo = 1;
        int totalCount;
        long numOfRows;
        boolean hasNext;

        do {
            OpenApiResponse res = openApiClient.getIndexInformationFromOpenApi(pageNo);
            totalCount = res.getTotalCount();
            numOfRows = res.getResponse().getBody().getNumOfRows();

            OpenApiResponse.Items items = res.getItems();
            if (items == null || items.getItems() == null || items.getItems().isEmpty()) {
                break; // 빈 페이지면 종료
            }

            items.getItems().stream()
                    .map(
                            item ->
                                    CreateIndexInformationCommand.of(
                                            item.getIndexClassification(),
                                            item.getIndexName(),
                                            item.getEmployedItemsCount(),
                                            item.getBasePointInTime(),
                                            item.getBaseIndex(),
                                            false))
                    .forEach(result::add);

            hasNext = pageNo * numOfRows < totalCount;
            pageNo++;

        } while (hasNext);

        return result;
    }
}
