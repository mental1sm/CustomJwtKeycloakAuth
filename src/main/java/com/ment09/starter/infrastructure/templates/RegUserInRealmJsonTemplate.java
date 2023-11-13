package com.ment09.starter.infrastructure.templates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ment09.starter.dto.TokenRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegUserInRealmJsonTemplate{

    private final ObjectMapper objectMapper;

    public String jsonBody(TokenRegistrationDTO tokenRegistrationDTO) throws JsonProcessingException {
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
