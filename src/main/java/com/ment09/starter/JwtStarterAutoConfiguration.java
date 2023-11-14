package com.ment09.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.config.CookieJwtFilter;
import com.ment09.starter.infrastructure.requests.*;
import com.ment09.starter.infrastructure.templates.*;
import com.ment09.starter.properties.CookieProperties;
import com.ment09.starter.properties.KeycloakProperties;
import com.ment09.starter.service.ConcreteTokenService;
import com.ment09.starter.service.TokenService;
import com.ment09.starter.util.CookieExtractor;
import com.ment09.starter.util.TokenCookieMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@AutoConfiguration
public class JwtStarterAutoConfiguration {

    @Bean
    public CookieProperties cookieProperties() {
        return new CookieProperties();
    }

    @Bean
    public KeycloakProperties keycloakProperties() {
        return new KeycloakProperties();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public CookieExtractor cookieExtractor() {
        return new CookieExtractor();
    }

    @Bean
    public TokenCookieMapper tokenCookieMapper(CookieProperties cookieProperties) {
        return new TokenCookieMapper(cookieProperties);
    }

    @Bean
    public CookieJwtFilter cookieJwtFilter(
            CookieExtractor cookieExtractor,
            TokenService tokenService,
            TokenCookieMapper tokenCookieMapper
    )
    {
        return new CookieJwtFilter(cookieExtractor, tokenService, tokenCookieMapper);
    }

    @Bean
    @ConditionalOnMissingBean(TokenService.class)
    public TokenService tokenService(
            AuthTokenRequest authTokenRequest,
            IntrospectTokenRequest introspectTokenRequest,
            RefreshTokenRequest refreshTokenRequest,
            AdminTokenRequest adminTokenRequest,
            RegUserInRealmRequest regUserInRealmRequest
    )
    {
        return new ConcreteTokenService(authTokenRequest, introspectTokenRequest, refreshTokenRequest, adminTokenRequest, regUserInRealmRequest);
    }

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

    @Bean
    public AuthEncodedUrlTemplate authEncodedUrlTemplate(KeycloakProperties keycloakProperties) {
        return new AuthEncodedUrlTemplate(keycloakProperties);
    }

    @Bean
    public IntrospectEncodedUrlTemplate introspectEncodedUrlTemplate(KeycloakProperties keycloakProperties) {
        return new IntrospectEncodedUrlTemplate(keycloakProperties);
    }

    @Bean
    public RefreshEncodedUrlTemplate refreshEncodedUrlTemplate(KeycloakProperties keycloakProperties) {
        return new RefreshEncodedUrlTemplate(keycloakProperties);
    }

    @Bean
    public AdminTokenEncodedUrlTemplate adminTokenEncodedUrlTemplate(KeycloakProperties keycloakProperties) {
        return new AdminTokenEncodedUrlTemplate(keycloakProperties);
    }

    @Bean
    public RegUserInRealmJsonTemplate regUserInRealmJsonTemplate(ObjectMapper objectMapper) {
        return new RegUserInRealmJsonTemplate(objectMapper);
    }

}
