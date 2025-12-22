package com.dev.workhiveback.exceptions;


import com.dev.workhiveback.results.reasons.login.LoginFailReason;
import lombok.Getter;

@Getter
public class LoginException extends RuntimeException{
    private final LoginFailReason reason;

    public LoginException(LoginFailReason reason, String message) {
        super(message);
        this.reason = reason;
    }
}
