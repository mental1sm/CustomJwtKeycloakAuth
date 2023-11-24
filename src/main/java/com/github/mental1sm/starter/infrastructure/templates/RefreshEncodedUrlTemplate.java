package com.github.mental1sm.starter.infrastructure.templates;

import com.github.mental1sm.starter.properties.AuthServerProperties;
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

    private final AuthServerProperties authServerProperties;

    /**
     * {@inheritDoc}
     */
    public MultiValueMap<String, String> encodedUrlBody(String refreshToken) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "refresh_token");
        data.add("refresh_token", refreshToken);
        data.add("client_id", authServerProperties.getClientId());
        data.add("client_secret", authServerProperties.getClientSecret());
        return data;
    }
}
