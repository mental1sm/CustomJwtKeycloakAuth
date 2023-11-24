package com.github.mental1sm.starter.infrastructure.requests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mental1sm.starter.domain.TokenPackWrapper;
import com.github.mental1sm.starter.dto.TokenAuthDTO;
import com.github.mental1sm.starter.infrastructure.templates.AuthEncodedUrlTemplate;
import com.github.mental1sm.starter.properties.AuthServerProperties;
import com.github.mental1sm.starter.util.exceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
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
    public TokenPackWrapper getAuthTokens(TokenAuthDTO tokenAuthDTO) throws JsonProcessingException, InvalidCredentialsException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = encodedUrlTemplate.encodedUrlBody(tokenAuthDTO);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(payload, headers);
        String tokenEndpointUrl = authServerProperties.getTokenUrl();

        try {
            ResponseEntity<String> response = authTemplate.exchange(tokenEndpointUrl, HttpMethod.POST, requestHttpEntity, String.class);
            return extractTokensFromResponseAsWrappedPack(objectMapper.readTree(response.getBody()));
        } catch (HttpClientErrorException e) {
            throw new InvalidCredentialsException();
        }
    }
}
