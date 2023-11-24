package com.github.mental1sm.starter.infrastructure.templates;

import com.github.mental1sm.starter.dto.TokenAuthDTO;
import com.github.mental1sm.starter.properties.AuthServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Тело запроса для получения Access и Redresh токенов от Keycloak
 */
@Component
@RequiredArgsConstructor
public class AuthEncodedUrlTemplate implements EncodedUrlTemplate<TokenAuthDTO> {

    private final AuthServerProperties authServerProperties;

    /**
     * {@inheritDoc}
    */
    public MultiValueMap<String, String> encodedUrlBody(TokenAuthDTO tokenAuthDTO) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "password");
        data.add("client_id", authServerProperties.getClientId());
        data.add("username", tokenAuthDTO.getUsername());
        data.add("password", tokenAuthDTO.getPassword());
        data.add("client_secret", authServerProperties.getClientSecret());
        return data;
    }
}
