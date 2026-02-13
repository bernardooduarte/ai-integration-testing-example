package com.bernardo_duarte.spring_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;


@SpringBootApplication
public class SpringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringApiApplication.class, args);
	}

	@Bean
	public RestClient.Builder restClientBuilder(RestClientCustomizer... customizers) {
		RestClient.Builder builder = RestClient.builder();
		for (RestClientCustomizer customizer : customizers) {
			customizer.customize(builder);
		}
		return builder;
	}

}
