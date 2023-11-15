package com.ment09.starter.infrastructure.templates;

import com.ment09.starter.properties.AuthServerProperties;
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

    private final AuthServerProperties authServerProperties;

    public MultiValueMap<String, String> encodedUrlBody() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "password");
        data.add("client_id", "admin-cli");
        data.add("username", authServerProperties.getAdminUsername());
        data.add("password", authServerProperties.getAdminPassword());
        data.add("client_secret", authServerProperties.getClientSecret());
        return data;
    }
}


