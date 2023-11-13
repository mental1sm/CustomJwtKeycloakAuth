package com.ment09.starter.infrastructure.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.domain.TokenPack;
import com.ment09.starter.domain.TokenPackWrapper;
import com.ment09.starter.properties.KeycloakProperties;
import com.ment09.starter.infrastructure.templates.RefreshEncodedUrlTemplate;
import com.ment09.starter.util.CookieExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Делает запрос на сервер аутентификации и возвращает новый токен
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenRequest extends BaseRequestWithTokenResponse {

    private final RefreshEncodedUrlTemplate refreshEncodedUrl;
    private final KeycloakProperties keycloakProperties;
    private final RestTemplate refreshTemplate;
    private final ObjectMapper objectMapper;

    public TokenPackWrapper refreshToken(String refreshToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = refreshEncodedUrl.encodedUrlBody(refreshToken);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = refreshTemplate.postForEntity(keycloakProperties.getTokenUrl(), requestHttpEntity, String.class);

        return extractTokensFromResponseAsWrappedPack(objectMapper.readTree(response.getBody()));
    }
}
