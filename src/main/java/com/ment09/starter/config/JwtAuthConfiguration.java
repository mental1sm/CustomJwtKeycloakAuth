package com.ment09.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.infrastructure.requests.*;
import com.ment09.starter.properties.CookieProperties;
import com.ment09.starter.service.ConcreteTokenService;
import com.ment09.starter.service.TokenService;
import com.ment09.starter.util.CookieExtractor;
import com.ment09.starter.util.TokenCookieMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtAuthConfiguration {

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
    public CookieJwtFilter cookieJwtFilter(
            CookieExtractor cookieExtractor,
            TokenService tokenService,
            TokenCookieMapper tokenCookieMapper
    )
    {
        return new CookieJwtFilter(cookieExtractor, tokenService, tokenCookieMapper);
    }

}
