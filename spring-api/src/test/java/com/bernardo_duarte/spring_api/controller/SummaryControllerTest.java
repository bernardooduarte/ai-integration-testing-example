package com.bernardo_duarte.spring_api.controller;

import com.bernardo_duarte.spring_api.dto.SummaryRequest;
import com.bernardo_duarte.spring_api.dto.SummaryResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SummaryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(options().port(8080));
        wireMockServer.start();
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
                        .withBody("{\"generated_text\": \"Resumo do WireMock\"}")));

        SummaryRequest request = new SummaryRequest("Texto para resumir");
        var response = restTemplate.postForEntity("/summarize", request, SummaryResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().success()).isTrue();
        assertThat(response.getBody().summary()).isEqualTo("Resumo do WireMock");
    }

    @Test
    void shouldReturnErrorWhenAiApiFails() {
        wireMockServer.stubFor(post(urlEqualTo("/v1/generate"))
                .willReturn(aResponse()
                        .withStatus(500)));

        SummaryRequest request = new SummaryRequest("Texto que causará erro");
        var response = restTemplate.postForEntity("/summarize", request, SummaryResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().success()).isFalse();
        assertThat(response.getBody().summary()).isEqualTo("Erro ao se comunicar com o serviço de IA");
    }
}
