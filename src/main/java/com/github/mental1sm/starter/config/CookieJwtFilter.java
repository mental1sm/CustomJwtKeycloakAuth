package com.github.mental1sm.starter.config;

import com.github.mental1sm.starter.domain.TokenPackWrapper;
import com.github.mental1sm.starter.service.TokenService;
import com.github.mental1sm.starter.util.CookieExtractor;
import com.github.mental1sm.starter.util.CustomConstants;
import com.github.mental1sm.starter.util.TokenAuthenticationWrapper;
import com.github.mental1sm.starter.util.TokenCookieMapper;
import com.github.mental1sm.starter.util.exceptions.InvalidCredentialsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


/**
 * Фильтр, отвечающий за извлечение, проверку, выдачу токенов.
 * Извлекает Access и Refreh токены из Куков и проводит инспекцию,
 * принимая решение о пропуске или отбраковке запроса
*/
@Component
@Slf4j
public class CookieJwtFilter extends OncePerRequestFilter {
    private final CookieExtractor cookieExtractor;
    private final TokenService tokenService;
    private final TokenCookieMapper tokenCookieMapper;

    public CookieJwtFilter(CookieExtractor cookieExtractor, TokenService tokenService, TokenCookieMapper tokenCookieMapper) {
        this.tokenService = tokenService;
        this.cookieExtractor = cookieExtractor;
        this.tokenCookieMapper = tokenCookieMapper;
    }

    /**
     * Фильтрует запрос
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("Начинаем проверку токена...");
        Optional<String> accessToken = cookieExtractor.extractValue(request, CustomConstants.TokenConstants.ACCESS_TOKEN.getValue());
        if (accessToken.isPresent()) {
            String realAccessToken = accessToken.get();
            tokenExists(realAccessToken, request, response, filterChain);
        }
        else {
            tokenIsNotExists(request, response, filterChain);
        }
    }

    /**
     * Метод, действующий в случае, если токен не найден
     */
    private void tokenIsNotExists(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Токен не найден. Ищем рефреш-токен...");
        refreshTokenCheck(request, response, filterChain);
    }

    /**
     * Метод, действующий в случае, если токен найден
     */
    private void tokenExists(String realAccessToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Токен существует. Проверяем срок истечения...");
        boolean healthy = tokenService.validateToken(realAccessToken);
        if (healthy) {
            log.info("Токен действителен.");
            TokenAuthenticationWrapper requestWrapper = new TokenAuthenticationWrapper(request, realAccessToken);
            filterChain.doFilter(requestWrapper, response);

        }
        else {
            log.info("Токен недействителен. Проверяем существование рефреш-токена...");
            refreshTokenCheck(request, response, filterChain);
        }
    }

    /**
     * Метод, действующий в случае, если Refresh токен найден
     */
    private void refreshExists(String realRefreshToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            log.info("Рефреш-токен найден. Запрашиваем новые токены...");
            TokenPackWrapper tokens = tokenService.refreshToken(realRefreshToken);
            tokenCookieMapper.mapToCookie(response, tokens.getAccess(), tokens.getRefresh());
            TokenAuthenticationWrapper authenticationWrapper = new TokenAuthenticationWrapper(request, tokens.getAccess().getToken());
            filterChain.doFilter(authenticationWrapper, response);
        } catch (HttpClientErrorException | InvalidCredentialsException e) {
            refreshIsNotExists(request, response, filterChain);
        }
    }

    /**
     * Метод, действующий в случае, если Refresh токен не найден
     */
    private void refreshIsNotExists(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Рефреш-токен не найден. Авторизация провалена.");
        filterChain.doFilter(request, response);
    }

    /**
     * Метод, проверяющий сам факт наличия Refresh токена в куках
     */
    private void refreshTokenCheck(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> refreshToken = cookieExtractor.extractValue(request, CustomConstants.TokenConstants.REFRESH_TOKEN.getValue());
        if (refreshToken.isPresent()) {
            refreshExists(refreshToken.get(), request, response, filterChain);
        }
        else {
            refreshIsNotExists(request, response, filterChain);
        }
    }
}
