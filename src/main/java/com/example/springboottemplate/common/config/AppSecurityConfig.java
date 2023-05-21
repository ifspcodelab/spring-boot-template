package com.example.springboottemplate.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.security")
@Getter
@Setter
public class AppSecurityConfig {
    private String issuer;
    private String secret;
    private Long accessTokenDurationInSeconds;
    private Long refreshTokenDurationInSeconds;
    private Long passwordResetDurationInSeconds;
}
