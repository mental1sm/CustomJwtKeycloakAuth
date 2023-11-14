package com.ment09.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.infrastructure.templates.*;
import com.ment09.starter.properties.AuthServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenTemplateConfig {

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
