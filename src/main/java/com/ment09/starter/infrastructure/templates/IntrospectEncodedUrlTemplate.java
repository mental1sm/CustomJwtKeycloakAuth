package com.ment09.starter.infrastructure.templates;

import com.ment09.starter.properties.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Тело запроса для интроспекции токена на сервере аутентификации Keycloak
 */
@Component
@RequiredArgsConstructor
public class IntrospectEncodedUrlTemplate implements EncodedUrlTemplate<String> {

    private final KeycloakProperties keycloakProperties;

    /**
     * {@inheritDoc}
     */
    public MultiValueMap<String, String> encodedUrlBody(String accessToken) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("token", accessToken);
        data.add("client_id", keycloakProperties.getClientId());
        data.add("client_secret", keycloakProperties.getClientSecret());
        return data;
    }
}
