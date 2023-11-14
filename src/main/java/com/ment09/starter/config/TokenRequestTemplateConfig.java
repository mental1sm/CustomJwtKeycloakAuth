package com.ment09.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.infrastructure.requests.*;
import com.ment09.starter.infrastructure.templates.*;
import com.ment09.starter.properties.AuthServerProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TokenRequestTemplateConfig {

    @Bean
    public AuthTokenRequest authTokenRequest(
            AuthEncodedUrlTemplate template,
            AuthServerProperties authServerProperties,
            ObjectMapper objectMapper,
            RestTemplate restTemplate
    )
    {
        return new AuthTokenRequest(template, authServerProperties, objectMapper, restTemplate);
    }

    @Bean
    public IntrospectTokenRequest introspectTokenRequest(
            IntrospectEncodedUrlTemplate template,
            AuthServerProperties authServerProperties,
            ObjectMapper objectMapper,
            RestTemplate restTemplate
    )
    {
        return new IntrospectTokenRequest(authServerProperties, template, objectMapper, restTemplate);
    }

    @Bean
    public RefreshTokenRequest refreshTokenRequest(
            RefreshEncodedUrlTemplate template,
            AuthServerProperties authServerProperties,
            RestTemplate restTemplate,
            ObjectMapper objectMapper
    )
    {
        return new RefreshTokenRequest(template, authServerProperties, restTemplate, objectMapper);
    }

    @Bean
    public AdminTokenRequest adminTokenRequest(
            ObjectMapper objectMapper,
            AdminTokenEncodedUrlTemplate adminTokenEncodedUrlTemplate,
            RestTemplate restTemplate,
            AuthServerProperties authServerProperties
    )
    {
        return new AdminTokenRequest(objectMapper, adminTokenEncodedUrlTemplate, restTemplate, authServerProperties);
    }

    @Bean
    public RegUserInRealmRequest regUserInRealmRequest(
            RegUserInRealmJsonTemplate regUserInRealmJsonTemplate,
            RestTemplate restTemplate,
            AuthServerProperties authServerProperties
    )
    {

        return new RegUserInRealmRequest(regUserInRealmJsonTemplate, restTemplate, authServerProperties);
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
