package com.ment09.starter.infrastructure.templates;

import com.ment09.starter.properties.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Тело запроса для обмена старого Refresh токена на новые Access и Refresh токен на сервере аутентификации Keycloak
 */
@Component
@RequiredArgsConstructor
public class RefreshEncodedUrlTemplate implements EncodedUrlTemplate<String> {

    private final KeycloakProperties keycloakProperties;

    /**
     * {@inheritDoc}
     */
    public MultiValueMap<String, String> encodedUrlBody(String refreshToken) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "refresh_token");
        data.add("refresh_token", refreshToken);
        data.add("client_id", keycloakProperties.getClientId());
        data.add("client_secret", keycloakProperties.getClientSecret());
        return data;
    }
}
