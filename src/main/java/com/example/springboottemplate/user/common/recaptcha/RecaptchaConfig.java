package com.example.springboottemplate.user.common.recaptcha;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.recaptcha")
public record RecaptchaConfig(Boolean enabled, String secretKey, String verifyUrl, Double scoreThreshold) {}
