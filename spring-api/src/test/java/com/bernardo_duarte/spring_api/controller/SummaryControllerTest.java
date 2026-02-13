package com.bernardo_duarte.spring_api.controller;

import com.bernardo_duarte.spring_api.dto.SummaryRequest;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "ai.api.url=http://localhost:8080")
public class SummaryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(options().port(8080));
        wireMockServer.start();

        wireMockServer.addMockServiceRequestListener((request, response) -> {
            System.out.println("WireMock recebeu: " + request.getUrl());
            System.out.println("Corpo enviado pela App: " + request.getBodyAsString());
        });
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void shouldSummarizeTextSuccessfully() {
        wireMockServer.stubFor(post(urlEqualTo("/v1/generate"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                              "generated_text": "Resumo do WireMock"
                            }
                            """)));

        SummaryRequest request = new SummaryRequest("Texto para resumir");

        ResponseEntity<String> response = restTemplate.postForEntity("/summarize", request, String.class);

        System.out.println("Status da Resposta (Sucesso): " + response.getStatusCode());
        System.out.println("Corpo da Resposta (Sucesso): " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Resumo do WireMock");
    }

    @Test
    void shouldReturnErrorWhenAiApiFails() {
        wireMockServer.stubFor(post(urlEqualTo("/v1/generate"))
                .willReturn(aResponse()
                        .withStatus(500)));

        SummaryRequest request = new SummaryRequest("Texto que causará erro");

        ResponseEntity<String> response = restTemplate.postForEntity("/summarize", request, String.class);

        System.out.println("Status da Resposta (Erro): " + response.getStatusCode());
        System.out.println("Corpo da Resposta (Erro): " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
    }
}