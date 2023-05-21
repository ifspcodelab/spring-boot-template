package com.example.springboottemplate.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.security")
public record AppSecurityConfig(
        String issuer,
        String secret,
        Long accessTokenDurationInSeconds,
        Long refreshTokenDurationInSeconds,
        Long passwordResetDurationInSeconds) {}
