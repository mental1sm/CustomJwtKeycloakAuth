package com.ment09.starter.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("/application.yaml")
public class CookieProperties {
    @Value("${spring.custom-adapter.cookie.http-only}")
    private boolean httpOnly;
    @Value("${spring.custom-adapter.cookie.secured}")
    private boolean secured;
    @Value("${spring.custom-adapter.cookie.trusted-domain}")
    private String trustedDomain;
    @Value("${spring.custom-adapter.cookie.path}")
    private String path;
}
