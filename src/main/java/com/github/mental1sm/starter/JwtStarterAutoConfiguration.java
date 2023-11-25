package com.github.mental1sm.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mental1sm.starter.config.ApiKeyFilter;
import com.github.mental1sm.starter.config.CookieJwtFilter;
import com.github.mental1sm.starter.infrastructure.requests.*;
import com.github.mental1sm.starter.infrastructure.templates.*;
import com.github.mental1sm.starter.properties.ApiKeyProperties;
import com.github.mental1sm.starter.properties.AuthServerProperties;
import com.github.mental1sm.starter.properties.CookieProperties;
import com.github.mental1sm.starter.service.ConcreteTokenService;
import com.github.mental1sm.starter.service.TokenService;
import com.github.mental1sm.starter.util.CookieExtractor;
import com.github.mental1sm.starter.util.TokenCookieMapper;
import com.github.mental1sm.starter.config.JwtConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

@AutoConfiguration
@ConditionalOnProperty(value = "spring.custom-auth.enable", havingValue = "true")
public class JwtStarterAutoConfiguration {

    @Bean
    public CookieProperties cookieProperties() {
        return new CookieProperties();
    }

    @Bean
    public AuthServerProperties keycloakProperties() {
        return new AuthServerProperties();
    }

    @Bean
    public ApiKeyProperties apiKeyProperties() {
        return new ApiKeyProperties();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.custom-auth.api-key.enabled", havingValue = "true")
    public ApiKeyFilter apiKeyFilter(ApiKeyProperties apiKeyProperties) {
        return new ApiKeyFilter(apiKeyProperties);
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
    @Order(2)
    public OncePerRequestFilter cookieJwtFilter(
            CookieExtractor cookieExtractor,
            TokenService tokenService,
            TokenCookieMapper tokenCookieMapper
    )
    {
        return new CookieJwtFilter(cookieExtractor, tokenService, tokenCookieMapper);
    }

    @Bean
    public JwtConverter jwtConverter() {
        return new JwtConverter();
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

    @Bean
    public AuthEncodedUrlTemplate authEncodedUrlTemplate(AuthServerProperties authServerProperties) {
        return new AuthEncodedUrlTemplate(authServerProperties);
    }

    @Bean
    public IntrospectEncodedUrlTemplate introspectEncodedUrlTemplate(AuthServerProperties authServerProperties) {
        return new IntrospectEncodedUrlTemplate(authServerProperties);
    }

    @Bean
    public RefreshEncodedUrlTemplate refreshEncodedUrlTemplate(AuthServerProperties authServerProperties) {
        return new RefreshEncodedUrlTemplate(authServerProperties);
    }

    @Bean
    public AdminTokenEncodedUrlTemplate adminTokenEncodedUrlTemplate(AuthServerProperties authServerProperties) {
        return new AdminTokenEncodedUrlTemplate(authServerProperties);
    }

    @Bean
    public RegUserInRealmJsonTemplate regUserInRealmJsonTemplate(ObjectMapper objectMapper) {
        return new RegUserInRealmJsonTemplate(objectMapper);
    }



}
