package com.eunsoly.findex.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient createCustomRestClient() {
        return RestClient.builder()
                .baseUrl(
                        "https://apis.data.go.kr/1160100/GetMarketIndexInfoService_V2/getStockMarketIndex_V2")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
