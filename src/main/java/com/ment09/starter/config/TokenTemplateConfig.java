package com.ment09.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.infrastructure.templates.*;
import com.ment09.starter.properties.KeycloakProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenTemplateConfig {

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
