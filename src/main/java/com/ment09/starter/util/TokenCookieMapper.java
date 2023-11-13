package com.ment09.starter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.starter.domain.TokenPack;
import com.ment09.starter.properties.CookieProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCookieMapper {

    private final CookieProperties cookieProperties;

    public void mapToCookie(HttpServletResponse response, TokenPack access, TokenPack refresh) throws JsonProcessingException {

        Cookie accessTokenCookie = new Cookie(CustomConstants.TokenConstants.ACCESS_TOKEN.getValue(), access.getToken());
        accessTokenCookie.setMaxAge(access.getLifeSpan());

        Cookie refreshTokenCookie = new Cookie(CustomConstants.TokenConstants.REFRESH_TOKEN.getValue(), refresh.getToken());
        refreshTokenCookie.setMaxAge(refresh.getLifeSpan());

        injectCookie(response, accessTokenCookie);
        injectCookie(response, refreshTokenCookie);
    }

    private void injectCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setSecure(cookieProperties.isSecured());
        cookie.setHttpOnly(cookieProperties.isHttpOnly());
        cookie.setPath(cookieProperties.getPath());
        cookie.setDomain(cookieProperties.getTrustedDomain());
        response.addCookie(cookie);
    }
}
