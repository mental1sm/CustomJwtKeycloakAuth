package com.github.mental1sm.starter.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties("spring.custom-auth.api-key")
@EnableConfigurationProperties
public class ApiKeyProperties {

    @Value("${spring.custom-auth.api-key.secret}")
    private String apiSecret;

    @Value("${spring.custom-auth.api-key.header-name}")
    private String headerName;

    @Value("${spring.custom-auth.api-key.enabled}")
    private String enabled;
}
