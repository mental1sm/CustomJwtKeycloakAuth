package com.ment09.starter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ment09.starter.domain.TokenPack;
import com.ment09.starter.properties.CookieProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Класс для маппинга токенов внутрь куки
*/
@Component
@RequiredArgsConstructor
public class TokenCookieMapper {

    private final CookieProperties cookieProperties;

    /**
     * Метод для маппинга токенов внутрь куков.
     * @param response Формирующийся ответ для текущего запроса
     * @param access Access токен
     * @param refresh Refresh токен
     */
    public void mapToCookie(HttpServletResponse response, TokenPack access, TokenPack refresh) {

        Cookie accessTokenCookie = new Cookie(CustomConstants.TokenConstants.ACCESS_TOKEN.getValue(), access.getToken());
        accessTokenCookie.setMaxAge(access.getLifeSpan());

        Cookie refreshTokenCookie = new Cookie(CustomConstants.TokenConstants.REFRESH_TOKEN.getValue(), refresh.getToken());
        refreshTokenCookie.setMaxAge(refresh.getLifeSpan());

        injectCookie(response, accessTokenCookie);
        injectCookie(response, refreshTokenCookie);
    }

    /**
     * Инъекия куков внутрь response
     * @param response Сервлетный response
     * @param cookie Кук для инъекции
    */
    private void injectCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setSecure(cookieProperties.isSecured());
        cookie.setHttpOnly(cookieProperties.isHttpOnly());
        cookie.setPath(cookieProperties.getPath());
        cookie.setDomain(cookieProperties.getTrustedDomain());
        response.addCookie(cookie);
    }
}
