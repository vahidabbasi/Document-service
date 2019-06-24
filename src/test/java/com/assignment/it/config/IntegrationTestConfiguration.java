package com.assignment.it.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class IntegrationTestConfiguration {

    @Bean("testRestTemplate")
    public RestTemplate testRestTemplate(final RestTemplateBuilder builder) {
        final RestTemplate restTemplate = builder
                .build();

        restTemplate.setRequestFactory(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        return restTemplate;
    }
}
