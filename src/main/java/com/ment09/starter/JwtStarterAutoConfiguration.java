package com.ment09.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.config.CookieJwtFilter;
import com.ment09.starter.config.JwtConverter;
import com.ment09.starter.infrastructure.requests.*;
import com.ment09.starter.infrastructure.templates.*;
import com.ment09.starter.properties.CookieProperties;
import com.ment09.starter.properties.AuthServerProperties;
import com.ment09.starter.service.ConcreteTokenService;
import com.ment09.starter.service.TokenService;
import com.ment09.starter.util.CookieExtractor;
import com.ment09.starter.util.TokenCookieMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

@AutoConfiguration
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
    JwtConverter jwtConverter() {
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
