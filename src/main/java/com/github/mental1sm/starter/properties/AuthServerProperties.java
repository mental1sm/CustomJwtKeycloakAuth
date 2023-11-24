package com.github.mental1sm.starter.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Свойства сервера авторизации Keycloak
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.custom-auth.server")
@EnableConfigurationProperties
public class AuthServerProperties {
    @Value("${spring.custom-auth.server.client-id}")
    private String clientId;
    @Value("${spring.custom-auth.server.token-url}")
    private String tokenUrl;
    @Value("${spring.custom-auth.server.introspect-url}")
    private String introspectUrl;
    @Value("${spring.custom-auth.server.client-secret}")
    private String clientSecret;
    @Value("${spring.custom-auth.server.admin-username}")
    private String adminUsername;
    @Value("${spring.custom-auth.server.admin-password}")
    private String adminPassword;
    @Value("${spring.custom-auth.server.admin-token-url}")
    private String adminTokenUrl;
    @Value("${spring.custom-auth.server.admin-users-url}")
    private String userManagementUrl;
}
