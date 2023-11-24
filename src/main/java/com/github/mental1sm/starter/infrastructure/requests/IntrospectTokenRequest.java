package com.github.mental1sm.starter.infrastructure.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mental1sm.starter.infrastructure.templates.IntrospectEncodedUrlTemplate;
import com.github.mental1sm.starter.properties.AuthServerProperties;
import com.github.mental1sm.starter.util.exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * Делает запрос на сервер аутентификации и возвращает валидность токена
 */
@Component
@RequiredArgsConstructor
public class IntrospectTokenRequest {

    private final AuthServerProperties authServerProperties;
    private final IntrospectEncodedUrlTemplate encodedUrl;
    private final ObjectMapper objectMapper;
    private final RestTemplate introspectTemplate;

    /**
     * Делает запрос на сервер аутентификации и возвращает валидность токена
     * @param accessToken Access token
     * @return Результат интрспекции: токен валиден либо невалиден
     */
    public boolean introspectAccessToken(String accessToken) throws JsonProcessingException, InvalidCredentialsException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = encodedUrl.encodedUrlBody(accessToken);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(payload, headers);
        String introspectionEndpointUrl = authServerProperties.getIntrospectUrl();
        ResponseEntity<String> response = introspectTemplate.postForEntity(introspectionEndpointUrl, requestHttpEntity, String.class);
        JsonNode responseNode = objectMapper.readTree(response.getBody());

        if (response.getStatusCode().value() != 200) {
            throw new InvalidCredentialsException();
        }

        return responseNode.get("active").toString().contentEquals("true");
    }
}
