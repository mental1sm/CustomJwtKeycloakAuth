package com.ment09.starter.infrastructure.requests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.domain.TokenPackWrapper;
import com.ment09.starter.dto.TokenAuthDTO;
import com.ment09.starter.properties.KeycloakProperties;
import com.ment09.starter.infrastructure.templates.AuthEncodedUrlTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Делает запрос на сервер аутентификации и возвращает новый токен
*/
@Service
@RequiredArgsConstructor
public class AuthTokenRequest extends BaseRequestWithTokenResponse {

    private final AuthEncodedUrlTemplate encodedUrlTemplate;
    private final KeycloakProperties keycloakProperties;
    private final ObjectMapper objectMapper;
    private final RestTemplate authTemplate;

    public TokenPackWrapper getAuthTokens(TokenAuthDTO tokenAuthDTO) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = encodedUrlTemplate.encodedUrlBody(tokenAuthDTO);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(payload, headers);
        String tokenEndpointUrl = keycloakProperties.getTokenUrl();
        ResponseEntity<String> response = authTemplate.postForEntity(tokenEndpointUrl, requestHttpEntity, String.class);

        return extractTokensFromResponseAsWrappedPack(objectMapper.readTree(response.getBody()));
    }
}
