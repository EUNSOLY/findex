package com.eunsoly.findex.client.openapi;

import com.eunsoly.findex.application.index.dto.CreateIndexDataCommand;
import com.eunsoly.findex.application.index.dto.CreateIndexInformationCommand;
import com.eunsoly.findex.application.index.port.ExternalIndexProvider;
import com.eunsoly.findex.application.integration.dto.IndexDataPage;
import com.eunsoly.findex.client.openapi.dto.OpenApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenApiClientService implements ExternalIndexProvider {
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

            items.getItems().stream().map(item -> CreateIndexInformationCommand.of(item.getIndexClassification(), item.getIndexName(),
                    item.getEmployedItemsCount(), item.getBasePointInTime(), item.getBaseIndex(), false)).forEach(result::add);

            hasNext = pageNo * numOfRows < totalCount;
            pageNo++;

        } while (hasNext);

        return result;
    }

    @Override
    public IndexDataPage getOpenApiIndexData(int pageNo, String indexClassification, String indexName, String baseDateFrom, String baseDateTo) {
        OpenApiResponse res = openApiClient.getIndexDataFromOpenApi(pageNo, indexName, baseDateFrom, baseDateTo);
        int totalCount = res.getTotalCount();
        long numOfRows = res.getResponse().getBody().getNumOfRows();
        boolean hasNext = pageNo * numOfRows < totalCount;

        OpenApiResponse.Items items = res.getItems();
        List<CreateIndexDataCommand> commands = items.getItems().stream().filter(item -> item.getIndexClassification().equals(indexClassification))
                .map(item -> CreateIndexDataCommand.of(null, item.getBaseDate(), item.getMarketPrice(), item.getClosingPrice(), item.getHighPrice(),
                        item.getLowPrice(), item.getVersus(), item.getFluctuationRate(), item.getTradingQuantity(), item.getTradingPrice(),
                        item.getMarketTotalAmount()))
                .toList();

        return IndexDataPage.of(commands, hasNext);
    }
}
