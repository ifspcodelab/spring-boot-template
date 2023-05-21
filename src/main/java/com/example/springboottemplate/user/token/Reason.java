package com.example.springboottemplate.user.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Reason {
    REFRESH_TOKEN_NOT_FOUND("Refresh token not found"),
    REFRESH_TOKEN_REVOKED("Refresh token revoked"),
    REFRESH_TOKEN_EXPIRED("Refresh token expired"),
    USER_LOCKED("Refresh token cannot be generated for a user locked");

    private final String message;
}
