package com.ment09.starter.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Свойства куки
*/
@Getter
@Configuration
@PropertySource("/application.yaml")
public class CookieProperties {
    @Value("${spring.custom-auth.cookie.http-only}")
    private boolean httpOnly;
    @Value("${spring.custom-auth.cookie.secured}")
    private boolean secured;
    @Value("${spring.custom-auth.cookie.trusted-domain}")
    private String trustedDomain;
    @Value("${spring.custom-auth.cookie.path}")
    private String path;
}
