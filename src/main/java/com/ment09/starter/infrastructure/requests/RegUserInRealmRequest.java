package com.ment09.starter.infrastructure.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ment09.starter.domain.TokenPack;
import com.ment09.starter.dto.TokenRegistrationDTO;
import com.ment09.starter.infrastructure.templates.RegUserInRealmJsonTemplate;
import com.ment09.starter.properties.AuthServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * Запрос к серверу авторизации Keycloak
 * Регистрирует от имени админа нового пользователя в конкретном реалме
 */
@Component
@RequiredArgsConstructor
public class RegUserInRealmRequest {

    private final RegUserInRealmJsonTemplate jsonTemplate;
    private final RestTemplate restTemplate;
    private final AuthServerProperties authServerProperties;

    /**
     * Запрос к серверу авторизации Keycloak
     * Регистрирует от имени админа нового пользователя в конкретном реалме
     * @param tokenRegistrationDTO Регистрационный ДТО
     * @param admin Запакованный токен администратора
     * @return успешность регистрации пользователя
     */
    public boolean regUserInRealm(TokenPack admin, TokenRegistrationDTO tokenRegistrationDTO) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", admin.getToken()));

        String payload = jsonTemplate.getJsonifiedString(tokenRegistrationDTO);
        HttpEntity<String> httpEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                authServerProperties.getUserManagementUrl(),
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {}
        );

        return response.getStatusCode().equals(HttpStatusCode.valueOf(200));
    }
}
