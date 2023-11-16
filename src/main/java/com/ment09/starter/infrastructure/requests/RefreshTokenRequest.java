package com.ment09.starter.infrastructure.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.domain.TokenPackWrapper;
import com.ment09.starter.properties.AuthServerProperties;
import com.ment09.starter.infrastructure.templates.RefreshEncodedUrlTemplate;
import com.ment09.starter.util.exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Запрос к серверу авторизации Keycloak для обновления токенов
 * Возвращает Access и Refresh токены
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenRequest extends BaseRequestWithTokenResponse {

    private final RefreshEncodedUrlTemplate refreshEncodedUrl;
    private final AuthServerProperties authServerProperties;
    private final RestTemplate refreshTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Запрос к серверу авторизации Keycloak для обновления токенов
     * Возвращает Access и Refresh токены
     * @param refreshToken Refresh токен
     * @return Оберточный класс с двумя токенами
     */
    public TokenPackWrapper refreshToken(String refreshToken) throws JsonProcessingException, InvalidCredentialsException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = refreshEncodedUrl.encodedUrlBody(refreshToken);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = refreshTemplate.postForEntity(authServerProperties.getTokenUrl(), requestHttpEntity, String.class);

        if (response.getStatusCode().value() != 200) {
            throw new InvalidCredentialsException();
        }

        return extractTokensFromResponseAsWrappedPack(objectMapper.readTree(response.getBody()));
    }
}
