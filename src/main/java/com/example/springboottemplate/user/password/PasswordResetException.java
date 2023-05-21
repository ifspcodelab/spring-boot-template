package com.example.springboottemplate.user.password;

import lombok.Getter;

@Getter
public class PasswordResetException extends RuntimeException {
    private final Reason reason;

    public PasswordResetException(Reason reason) {
        super(reason.getMessage());
        this.reason = reason;
    }
}
