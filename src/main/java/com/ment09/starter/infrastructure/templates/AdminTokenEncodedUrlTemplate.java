package com.ment09.starter.infrastructure.templates;

import com.ment09.starter.properties.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Тело запроса для получения admin-токена от Keycloak
*/
@Component
@RequiredArgsConstructor
public class AdminTokenEncodedUrlTemplate {

    private final KeycloakProperties keycloakProperties;

    public MultiValueMap<String, String> encodedUrlBody() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "password");
        data.add("client_id", keycloakProperties.getClientId());
        data.add("username", keycloakProperties.getAdminUsername());
        data.add("password", keycloakProperties.getAdminPassword());
        data.add("client_secret", keycloakProperties.getClientSecret());
        return data;
    }
}


