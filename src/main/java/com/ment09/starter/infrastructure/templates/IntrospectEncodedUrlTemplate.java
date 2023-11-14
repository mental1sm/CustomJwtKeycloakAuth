package com.ment09.starter.infrastructure.templates;

import com.ment09.starter.properties.AuthServerProperties;
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

    private final AuthServerProperties authServerProperties;

    /**
     * {@inheritDoc}
     */
    public MultiValueMap<String, String> encodedUrlBody(String accessToken) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("token", accessToken);
        data.add("client_id", authServerProperties.getClientId());
        data.add("client_secret", authServerProperties.getClientSecret());
        return data;
    }
}
