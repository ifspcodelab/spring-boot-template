package com.example.springboottemplate.user.common.recaptcha;

public class RecaptchaException extends RuntimeException {
    public RecaptchaException() {
        super("Invalid recaptcha token");
    }
}
