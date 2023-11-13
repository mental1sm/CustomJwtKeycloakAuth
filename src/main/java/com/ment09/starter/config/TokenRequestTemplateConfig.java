package com.ment09.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.infrastructure.requests.*;
import com.ment09.starter.infrastructure.templates.*;
import com.ment09.starter.properties.KeycloakProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TokenRequestTemplateConfig {

    @Bean
    public AuthTokenRequest authTokenRequest(
            AuthEncodedUrlTemplate template,
            KeycloakProperties keycloakProperties,
            ObjectMapper objectMapper,
            RestTemplate restTemplate
    )
    {
        return new AuthTokenRequest(template, keycloakProperties, objectMapper, restTemplate);
    }

    @Bean
    public IntrospectTokenRequest introspectTokenRequest(
            IntrospectEncodedUrlTemplate template,
            KeycloakProperties keycloakProperties,
            ObjectMapper objectMapper,
            RestTemplate restTemplate
    )
    {
        return new IntrospectTokenRequest(keycloakProperties, template, objectMapper, restTemplate);
    }

    @Bean
    public RefreshTokenRequest refreshTokenRequest(
            RefreshEncodedUrlTemplate template,
            KeycloakProperties keycloakProperties,
            RestTemplate restTemplate,
            ObjectMapper objectMapper
    )
    {
        return new RefreshTokenRequest(template, keycloakProperties, restTemplate, objectMapper);
    }

    @Bean
    public AdminTokenRequest adminTokenRequest(
            ObjectMapper objectMapper,
            AdminTokenEncodedUrlTemplate adminTokenEncodedUrlTemplate,
            RestTemplate restTemplate,
            KeycloakProperties keycloakProperties
    )
    {
        return new AdminTokenRequest(objectMapper, adminTokenEncodedUrlTemplate, restTemplate, keycloakProperties);
    }

    @Bean
    public RegUserInRealmRequest regUserInRealmRequest(
            RegUserInRealmJsonTemplate regUserInRealmJsonTemplate,
            RestTemplate restTemplate,
            KeycloakProperties keycloakProperties
    )
    {

        return new RegUserInRealmRequest(regUserInRealmJsonTemplate, restTemplate, keycloakProperties);
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
