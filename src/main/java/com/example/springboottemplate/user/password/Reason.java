package com.example.springboottemplate.user.password;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Reason {
    USER_NOT_FOUND("User not found"),
    USER_DISABLED("User did not confirm verification email"),
    USER_LOCKED("Password reset cannot be generated for a user locked"),
    PASSWORD_RESET_ALREADY_EXISTS("Password reset already exists"),
    PASSWORD_RESET_NOT_FOUND("Password reset not found"),
    PASSWORD_RESET_USED("Password reset already used"),
    PASSWORD_RESET_EXPIRED("Password reset expired");

    private final String message;
}
