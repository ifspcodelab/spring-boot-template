package com.example.springboottemplate.user.token;

import lombok.Getter;

@Getter
public class TokenRotationException extends RuntimeException {
    private final Reason reason;

    public TokenRotationException(Reason reason) {
        super(reason.getMessage());
        this.reason = reason;
    }
}
