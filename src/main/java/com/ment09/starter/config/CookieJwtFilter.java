package com.ment09.starter.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ment09.starter.domain.TokenPackWrapper;
import com.ment09.starter.service.TokenService;
import com.ment09.starter.util.CookieExtractor;
import com.ment09.starter.util.CustomConstants;
import com.ment09.starter.util.TokenAuthenticationWrapper;
import com.ment09.starter.util.TokenCookieMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CookieJwtFilter extends OncePerRequestFilter {
    private final CookieExtractor cookieExtractor;
    private final TokenService tokenService;
    private final TokenCookieMapper tokenCookieMapper;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    public CookieJwtFilter(CookieExtractor cookieExtractor, TokenService tokenService, TokenCookieMapper tokenCookieMapper) {
        this.tokenService = tokenService;
        this.cookieExtractor = cookieExtractor;
        this.tokenCookieMapper = tokenCookieMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        this.filterChain = filterChain;

        log.info("Начинаем проверку токена...");
        Optional<String> accessToken = cookieExtractor.extractValue(request, CustomConstants.TokenConstants.ACCESS_TOKEN.getValue());
        if (accessToken.isPresent()) {
            String realAccessToken = accessToken.get();
            tokenExists(realAccessToken);
        }
        else {
            tokenIsNotExists();
        }
    }

    private void tokenIsNotExists() throws ServletException, IOException {
        log.info("Токен не найден. Ищем рефреш-токен...");
        refreshTokenCheck();
    }

    private void tokenExists(String realAccessToken) throws ServletException, IOException {
        log.info("Токен существует. Проверяем срок истечения...");
        boolean healthy = tokenService.validateToken(realAccessToken);
        if (healthy) {
            log.info("Токен действителен.");
            TokenAuthenticationWrapper requestWrapper = new TokenAuthenticationWrapper(request, realAccessToken);
            this.filterChain.doFilter(requestWrapper, response);

        }
        else {
            log.info("Токен недействителен. Проверяем существование рефреш-токена...");
            refreshTokenCheck();
        }
    }

    private void refreshExists(String realRefreshToken) throws IOException, ServletException {
        try {
            log.info("Рефреш-токен найден. Запрашиваем новые токены...");
            TokenPackWrapper tokens = tokenService.refreshToken(realRefreshToken);
            tokenCookieMapper.mapToCookie(response, tokens.getAccess(), tokens.getRefresh());
            TokenAuthenticationWrapper authenticationWrapper = new TokenAuthenticationWrapper(request, tokens.getAccess().getToken());
            filterChain.doFilter(authenticationWrapper, response);
        } catch (HttpClientErrorException e) {
            refreshIsNotExists();
        }
    }

    private void refreshIsNotExists() throws ServletException, IOException {
        log.info("Рефреш-токен не найден. Авторизация провалена.");
        filterChain.doFilter(request, response);
    }

    private void refreshTokenCheck() throws ServletException, IOException {
        Optional<String> refreshToken = cookieExtractor.extractValue(request, CustomConstants.TokenConstants.REFRESH_TOKEN.getValue());
        if (refreshToken.isPresent()) {
            refreshExists(refreshToken.get());
        }
        else {
            refreshIsNotExists();
        }
    }
}
