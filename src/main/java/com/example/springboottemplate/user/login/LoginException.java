package com.example.springboottemplate.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum Reason {
    WRONG_EMAIL_OR_PASSWORD("Wrong email or password"),
    USER_DISABLED("User did not confirm verification email"),
    USER_LOCKED("User was locked by admin");
    private final String message;
}

@Getter
public class LoginException extends RuntimeException {
    private final Reason reason;

    public LoginException(Reason reason) {
        super(reason.getMessage());
        this.reason = reason;
    }
}
