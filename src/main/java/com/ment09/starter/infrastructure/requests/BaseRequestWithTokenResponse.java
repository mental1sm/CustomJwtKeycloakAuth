package com.ment09.starter.infrastructure.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.ment09.starter.domain.TokenPack;
import com.ment09.starter.domain.TokenPackWrapper;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseRequestWithTokenResponse {

    public BaseRequestWithTokenResponse(){}

    protected TokenPackWrapper extractTokensFromResponseAsWrappedPack(JsonNode jsonNode) {
        TokenPack access = TokenPack.builder()
                .token(jsonNode.get("access_token").toString().replace("\"", ""))
                .lifeSpan(Integer.parseInt(jsonNode.get("expires_in").toString()))
                .build();

        TokenPack refresh = TokenPack.builder()
                .token(jsonNode.get("refresh_token").toString().replace("\"", ""))
                .lifeSpan(Integer.parseInt(jsonNode.get("refresh_expires_in").toString()))
                .build();

        return TokenPackWrapper.builder().access(access).refresh(refresh).build();
    }
}
