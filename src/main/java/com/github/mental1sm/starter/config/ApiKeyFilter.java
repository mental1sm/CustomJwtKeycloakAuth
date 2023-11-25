package com.github.mental1sm.starter.config;

import com.github.mental1sm.starter.properties.ApiKeyProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class ApiKeyFilter extends OncePerRequestFilter {

    private final ApiKeyProperties apiKeyProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        Optional<String> apiKey = getApiKey(request);
        if (apiKey.isPresent()) {
            String apiKeyValue = apiKey.get();
            if (apiKeyValue.contentEquals(apiKeyProperties.getApiSecret())) {
                ApiKeyAuthenticationToken apiKeyAuthenticationToken = new ApiKeyAuthenticationToken(
                        apiKeyValue, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
                SecurityContextHolder.getContext().setAuthentication(apiKeyAuthenticationToken);
            }
        }
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
}
