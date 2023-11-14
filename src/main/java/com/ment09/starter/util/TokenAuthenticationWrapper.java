package com.ment09.starter.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * Класс обертки для инъекции Access токена в заголовок Authorization
*/
public class TokenAuthenticationWrapper extends HttpServletRequestWrapper {


    private final String token;

    public TokenAuthenticationWrapper(HttpServletRequest request, String token) {
        super(request);
        this.token = token;
    }

    @Override
    public String getHeader(String name) {
        if ("Authorization".equalsIgnoreCase(name)) {
            return "Bearer " + token;
        }
        return super.getHeader(name);
    }
}