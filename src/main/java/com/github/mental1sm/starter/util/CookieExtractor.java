package com.github.mental1sm.starter.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Класс для извлечения параметра из куков
*/
@Component
@RequiredArgsConstructor
public class CookieExtractor {
    /**
     * Метод извлекает значение параметра из куков
     * @param request Сервлетный запрос
     * @param cookieName имя параметра
     * @return Значение, либо null
    */
    public Optional<String> extractValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().contentEquals(cookieName)) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }
}
