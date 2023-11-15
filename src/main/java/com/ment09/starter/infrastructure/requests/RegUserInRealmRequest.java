package com.ment09.starter.infrastructure.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ment09.starter.domain.TokenPack;
import com.ment09.starter.dto.TokenRegistrationDTO;
import com.ment09.starter.infrastructure.templates.RegUserInRealmJsonTemplate;
import com.ment09.starter.properties.AuthServerProperties;
import com.ment09.starter.util.exceptions.UserIsAlreadyExistsException;
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
    public int regUserInRealm(TokenPack admin, TokenRegistrationDTO tokenRegistrationDTO) throws JsonProcessingException, UserIsAlreadyExistsException {
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

        if (response.getStatusCode().value() != 201) {
            throw new UserIsAlreadyExistsException(response.getBody());
        }

        return response.getStatusCode().value(); // 201 is Created; 401 Unauthorized; 404 Realm not found
    }
}
