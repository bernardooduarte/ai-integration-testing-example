package com.bernardo_duarte.spring_api.service;

import com.bernardo_duarte.spring_api.dto.IaRawResponse;
import com.bernardo_duarte.spring_api.dto.SummaryRequest;
import com.bernardo_duarte.spring_api.dto.SummaryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SummaryService {
    private final RestClient restClient;

    public SummaryService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://api.fake-ai.com").build();
    }

    public SummaryResponse summarize(String text) {
        IaRawResponse response = restClient.post()
                .uri("/v1/generate")
                .body(new SummaryRequest(text))
                .retrieve()
                .body(IaRawResponse.class);

        return new SummaryResponse(true, response.generatedText());
    }
}
