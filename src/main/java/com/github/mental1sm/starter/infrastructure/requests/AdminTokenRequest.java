package com.github.mental1sm.starter.infrastructure.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mental1sm.starter.infrastructure.templates.AdminTokenEncodedUrlTemplate;
import com.github.mental1sm.starter.properties.AuthServerProperties;
import com.github.mental1sm.starter.domain.TokenPack;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

/**
 * Запрос к серверу авторизации Keycloak
 * Возвращает токен администратора Keycloak
*/
@Service
@RequiredArgsConstructor
public class AdminTokenRequest {

    private final ObjectMapper objectMapper;
    private final AdminTokenEncodedUrlTemplate encodedUrlTemplate;
    private final RestTemplate restTemplate;
    private final AuthServerProperties authServerProperties;

    /**
     * Метод для получения токена администратора,
     * @return TokenPack - запакованный admin токен
     */
    public TokenPack getAdminToken() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = encodedUrlTemplate.encodedUrlBody();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(authServerProperties.getAdminTokenUrl(), httpEntity, String.class);
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return TokenPack.builder()
                .token(jsonNode.get("access_token").toString().replace("\"", ""))
                .lifeSpan(Integer.parseInt(jsonNode.get("expires_in").toString()))
                .build();
    }
}
