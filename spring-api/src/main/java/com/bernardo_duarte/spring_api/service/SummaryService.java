package com.bernardo_duarte.spring_api.service;

import com.bernardo_duarte.spring_api.dto.IaRawResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Service
public class SummaryService {

    private final RestClient restClient;

    public SummaryService(RestClient.Builder builder,
                          @Value("${ai.api.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public String summarize(String text) {
        try {
            Map<String, String> body = Map.of("text", text);

            IaRawResponse response = restClient.post()
                    .uri("/v1/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(IaRawResponse.class);

            return response.generated_text();

        } catch (RestClientException ex) {
            throw new RuntimeException("Erro ao chamar API de IA", ex);
        }
    }
}