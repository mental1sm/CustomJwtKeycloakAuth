package com.ment09.starter.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Свойства сервера авторизации Keycloak
 */
@Getter
@Configuration
@PropertySource("/application.yaml")
public class KeycloakProperties {
    @Value("${spring.custom-adapter.keycloak.client-id}")
    private String clientId;
    @Value("${spring.custom-adapter.keycloak.token-url}")
    private String tokenUrl;
    @Value("${spring.custom-adapter.keycloak.introspect-url}")
    private String introspectUrl;
    @Value("${spring.custom-adapter.keycloak.client-secret}")
    private String clientSecret;
    @Value("${spring.custom-adapter.keycloak.admin-username}")
    private String adminUsername;
    @Value("${spring.custom-adapter.keycloak.admin-password}")
    private String adminPassword;
    @Value("${spring.custom-adapter.keycloak.admin-token-url}")
    private String adminTokenUrl;
    @Value("${spring.custom-adapter.keycloak.admin-users-url}")
    private String userManagementUrl;
}
