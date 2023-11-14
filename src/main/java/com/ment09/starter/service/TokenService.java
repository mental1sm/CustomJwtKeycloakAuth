package com.ment09.starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ment09.starter.domain.TokenPackWrapper;
import com.ment09.starter.dto.TokenAuthDTO;
import com.ment09.starter.dto.TokenRegistrationDTO;

/**
 * Сервис, отвечающий за все манипуляции,
 * связанные с JWT токенами авторизации
 */
public interface TokenService {
    /**
     * Регистрирует юзера в реалме
     * @param tokenRegistrationDTO Данные регистрационной формы
    */
    void registerNewUser(TokenRegistrationDTO tokenRegistrationDTO) throws JsonProcessingException;

    /**
     * Авторизует юзера в реалме
     *
     * @param tokenAuthDTO Данные авторизационной формы
     * @return Map из access и refresh токена. Доступ получается по константам
     */
    TokenPackWrapper authUser(TokenAuthDTO tokenAuthDTO) throws JsonProcessingException;

    /**
     * Проверяет время существования токена
     * @param accessToken Access токен
     * @return валидность токена
    */
    boolean validateToken(String accessToken);

    /**
     * Проверяет токен на интроспекции
     * @param accessToken Access токен
     * @return результат интроспекции токена, то есть его валидность
    */
    boolean introspectToken(String accessToken) throws JsonProcessingException;

    /**
     * Выдает новый bearer access token в обмен на refresh token
     * @param refreshToken Refresh токен
     * @return оберточный класс, содержащий внутри себя оба токена
     */
    TokenPackWrapper refreshToken(String refreshToken) throws JsonProcessingException;
}
