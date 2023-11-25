package com.github.mental1sm.starter.config;

import com.github.mental1sm.starter.properties.ApiKeyProperties;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


/**
 * Фильтр извлекает из заголовка ApiToken (Если таковой имеется)
 * И выдает роль администратора, если токен верен
*/
@Component
@Slf4j
@RequiredArgsConstructor
public class ApiKeyFilter implements Filter, Ordered {

    private final ApiKeyProperties apiKeyProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        log.info("Начинаем проверку API-ключа...");
        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            Optional<String> apiKey = getApiKey((HttpServletRequest) request);
            if (apiKey.isPresent()) {
                log.info("API-ключ найден. Начинаем проверку.");
                String apiKeyValue = apiKey.get();
                if (apiKeyValue.contentEquals(apiKeyProperties.getApiSecret())) {
                    log.info("API-ключ верен.");
                    UserDetails anonymousUser = new User("api-key-user", "", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));

                    ApiKeyAuthenticationToken apiKeyAuthenticationToken = new ApiKeyAuthenticationToken(
                            apiKeyValue, anonymousUser.getAuthorities());
                    apiKeyAuthenticationToken.setDetails(anonymousUser);
                    SecurityContextHolder.getContext().setAuthentication(apiKeyAuthenticationToken);

                }
            }
        }
        log.info("Продолжаем фильтрацию.");
        filterChain.doFilter(request, response);

    }

    private Optional<String> getApiKey(HttpServletRequest request) {
        Optional<String> optionalApiKey = Optional.empty();

        String apiKeyHeader = request.getHeader(apiKeyProperties.getHeaderName());
        if (apiKeyHeader != null) {
            apiKeyHeader = apiKeyHeader.trim();
            optionalApiKey = Optional.of(apiKeyHeader);
        }
        return optionalApiKey;
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
