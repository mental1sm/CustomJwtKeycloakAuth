package com.ment09.starter.infrastructure.requests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.domain.TokenPackWrapper;
import com.ment09.starter.dto.TokenAuthDTO;
import com.ment09.starter.properties.AuthServerProperties;
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
 * Запрос к серверу авторизации Keycloak
 * Возвращает Access и Refresh токены
 */
@Service
@RequiredArgsConstructor
public class AuthTokenRequest extends BaseRequestWithTokenResponse {

    private final AuthEncodedUrlTemplate encodedUrlTemplate;
    private final AuthServerProperties authServerProperties;
    private final ObjectMapper objectMapper;
    private final RestTemplate authTemplate;

    /**
     * Метод для получения оберточного класса TokenPackWrapper,
     * несущего в себе как Access, так и Refreh токены
     * @param tokenAuthDTO ДТО авторизации
     * @return оберточный класс TokenPackWrapper, содержащий внутри оба токена
    */
    public TokenPackWrapper getAuthTokens(TokenAuthDTO tokenAuthDTO) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = encodedUrlTemplate.encodedUrlBody(tokenAuthDTO);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(payload, headers);
        String tokenEndpointUrl = authServerProperties.getTokenUrl();
        ResponseEntity<String> response = authTemplate.postForEntity(tokenEndpointUrl, requestHttpEntity, String.class);

        return extractTokensFromResponseAsWrappedPack(objectMapper.readTree(response.getBody()));
    }
}
