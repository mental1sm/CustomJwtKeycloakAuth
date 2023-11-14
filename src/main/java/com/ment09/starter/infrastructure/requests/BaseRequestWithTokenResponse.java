package com.ment09.starter.infrastructure.requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.ment09.starter.domain.TokenPack;
import com.ment09.starter.domain.TokenPackWrapper;
import org.springframework.stereotype.Component;

/**
 * Абстрактный класс запросов, применяемый только для тех запросов, ответ которых будет содержать Access и Refresh токен.
 * Содержит в себе метод для извлечения токенов и упаковки их в оберточный класс TokenPackWrapper
 */
@Component
public abstract class BaseRequestWithTokenResponse {

    public BaseRequestWithTokenResponse(){}

    /**
     * метод для извлечения токенов и упаковки их в оберточный класс TokenPackWrapper
     * @param jsonNode json нода, замапленная от ответа, содержащего токены в своем теле
     * @return TokenPackWrapper оберточный класс, в котором запакованы Access и Refresh токены
    */
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
