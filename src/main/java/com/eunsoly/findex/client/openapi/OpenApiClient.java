package com.eunsoly.findex.client.openapi;

import com.eunsoly.findex.client.openapi.dto.OpenApiResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class OpenApiClient {

    private final String apiKey;
    private final RestClient restClient;
    private final String apiRowCount;

    public OpenApiClient(
            @Value("${findex.openapi.key}") String apiKey,
            @Value("${findex.openapi.rowcount}") String rowCount,
            RestClient restClient) {
        this.apiKey = apiKey;
        this.restClient = restClient;
        this.apiRowCount = rowCount;
    }

    private String getLastDate() {
        LocalDate todayDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate beginDate = todayDate.minusDays(10);
        String today = todayDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String begin = beginDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        OpenApiResponse response =
                restClient
                        .get()
                        .uri(
                                uriBuilder ->
                                        uriBuilder
                                                .queryParam("serviceKey", apiKey)
                                                .queryParam("resultType", "json")
                                                .queryParam("pageNo", 1)
                                                .queryParam("numOfRows", apiRowCount)
                                                .queryParam("beginBasDt", begin)
                                                .queryParam("endBasDt", today)
                                                .build())
                        .retrieve()
                        .body(OpenApiResponse.class);

        return Optional.ofNullable(response)
                .map(OpenApiResponse::getResponse)
                .map(OpenApiResponse.Response::getBody)
                .map(OpenApiResponse.Body::getItems)
                .map(OpenApiResponse.Items::getItems)
                .flatMap(
                        item ->
                                item.stream()
                                        .map(OpenApiResponse.Item::getBaseDate)
                                        .max(Comparator.naturalOrder())
                                        .map(
                                                date ->
                                                        date.format(
                                                                DateTimeFormatter.ofPattern(
                                                                        "yyyyMMdd"))))
                .orElseThrow(() -> new RuntimeException("")); // TODO: API용 Exception 생성하여 교체하기
    }

    public OpenApiResponse getIndexInformationFromOpenApi(int pageNo) {
        String baseDate = this.getLastDate();

        return restClient
                .get()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .queryParam("serviceKey", apiKey)
                                        .queryParam("resultType", "json")
                                        .queryParam("pageNo", pageNo)
                                        .queryParam("numOfRows", apiRowCount)
                                        .queryParam("basDt", baseDate)
                                        .build())
                .retrieve()
                .body(OpenApiResponse.class);
    }

    public OpenApiResponse getIndexDataFromOpenApi(
            int pageNo, String indexName, String baseDateFrom, String baseDateTo) {
        String resultDateTo =
                LocalDate.parse(baseDateTo)
                        .plusDays(1)
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return restClient
                .get()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .queryParam("serviceKey", apiKey)
                                        .queryParam("resultType", "json")
                                        .queryParam("pageNo", pageNo)
                                        .queryParam("numOfRows", apiRowCount)
                                        .queryParam("idxNm", indexName)
                                        .queryParam("beginBasDt", baseDateFrom)
                                        .queryParam("endBasDt", resultDateTo)
                                        .build())
                .retrieve()
                .body(OpenApiResponse.class);
    }
}
