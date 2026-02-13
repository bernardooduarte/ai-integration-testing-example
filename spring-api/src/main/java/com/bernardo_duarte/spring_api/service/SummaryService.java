package com.bernardo_duarte.spring_api.service;

import com.bernardo_duarte.spring_api.dto.IaRawResponse;
import com.bernardo_duarte.spring_api.dto.SummaryRequest;
import com.bernardo_duarte.spring_api.dto.SummaryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SummaryService {
    private final RestClient restClient;

    public SummaryService(RestClient.Builder builder, @Value("${ai.api.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public SummaryResponse summarize(String text) {
        java.util.Map<String, String> bodyMap = java.util.Map.of("text", text);

        IaRawResponse response = restClient.post()
                .uri("/v1/generate")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(bodyMap)
                .retrieve()
                .body(IaRawResponse.class);

        return new SummaryResponse(true, response.generatedText());
    }
}
