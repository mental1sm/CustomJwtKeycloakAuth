package com.github.mental1sm.starter.infrastructure.templates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mental1sm.starter.dto.TokenRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Тело запроса для регистрации нового пользователя на сервере аутентификации Keycloak
 */
@Component
@RequiredArgsConstructor
public class RegUserInRealmJsonTemplate implements JsonTemplate<TokenRegistrationDTO> {

    private final ObjectMapper objectMapper;

    /**
     * {@inheritDoc}
    */
    public String getJsonifiedString(TokenRegistrationDTO tokenRegistrationDTO) throws JsonProcessingException {
        ObjectNode json = objectMapper.createObjectNode();
        json.put("username", tokenRegistrationDTO.getUsername());
        json.put("email", tokenRegistrationDTO.getEmail());
        json.put("enabled", "true");
        ArrayNode credentialsNode = objectMapper.createArrayNode();
        ObjectNode credentialsObject = objectMapper.createObjectNode();
        credentialsObject.put("type", "password");
        credentialsObject.put("value", tokenRegistrationDTO.getPassword());
        credentialsNode.add(credentialsObject);
        json.set("credentials", credentialsNode);

        return objectMapper.writeValueAsString(json);
    }
}
