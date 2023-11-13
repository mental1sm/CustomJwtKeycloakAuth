package com.ment09.starter.infrastructure.templates;

import com.ment09.starter.dto.TokenAuthDTO;
import com.ment09.starter.properties.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthEncodedUrlTemplate implements EncodedUrlTemplate<TokenAuthDTO> {

    private final KeycloakProperties keycloakProperties;

    public MultiValueMap<String, String> encodedUrlBody(TokenAuthDTO tokenAuthDTO) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "password");
        data.add("client_id", keycloakProperties.getClientId());
        data.add("username", tokenAuthDTO.getUsername());
        data.add("password", tokenAuthDTO.getPassword());
        data.add("client_secret", keycloakProperties.getClientSecret());
        return data;
    }
}
