package com.ment09.starter.infrastructure.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.properties.AuthServerProperties;
import com.ment09.starter.infrastructure.templates.IntrospectEncodedUrlTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public boolean introspectAccessToken(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = encodedUrl.encodedUrlBody(accessToken);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(payload, headers);
        String introspectionEndpointUrl = authServerProperties.getIntrospectUrl();
        String responseBody = introspectTemplate.postForEntity(introspectionEndpointUrl, requestHttpEntity, String.class).getBody();
        JsonNode responseNode = objectMapper.readTree(responseBody);

        return responseNode.get("active").toString().contentEquals("true");
    }
}
