package com.ment09.starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ment09.starter.domain.TokenPackWrapper;
import com.ment09.starter.dto.TokenAuthDTO;
import com.ment09.starter.dto.TokenRegistrationDTO;
import com.ment09.starter.util.exceptions.InvalidCredentialsException;
import com.ment09.starter.util.exceptions.UserIsAlreadyExistsException;

/**
 * Сервис, отвечающий за все манипуляции,
 * связанные с JWT токенами авторизации
 */
public interface TokenService {
    /**
     * Регистрирует юзера в реалме
     * @param tokenRegistrationDTO Данные регистрационной формы
    */
    int registerNewUser(TokenRegistrationDTO tokenRegistrationDTO) throws JsonProcessingException, UserIsAlreadyExistsException;

    /**
     * Авторизует юзера в реалме
     *
     * @param tokenAuthDTO Данные авторизационной формы
     * @return Map из access и refresh токена. Доступ получается по константам
     */
    TokenPackWrapper authUser(TokenAuthDTO tokenAuthDTO) throws JsonProcessingException, InvalidCredentialsException;

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
    boolean introspectToken(String accessToken) throws JsonProcessingException, InvalidCredentialsException;

    /**
     * Выдает новый bearer access token в обмен на refresh token
     * @param refreshToken Refresh токен
     * @return оберточный класс, содержащий внутри себя оба токена
     */
    TokenPackWrapper refreshToken(String refreshToken) throws JsonProcessingException, InvalidCredentialsException;
}
