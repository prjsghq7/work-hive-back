package com.dev.workhiveback.exceptions;


import com.dev.workhiveback.exceptions.user.ReasonedException;
import com.dev.workhiveback.results.reasons.login.LoginFailReason;
import lombok.Getter;

@Getter
public class LoginException extends RuntimeException implements ReasonedException {
    private final LoginFailReason reason;

    public LoginException(LoginFailReason reason, String message) {
        super(message);
        this.reason = reason;
    }
    @Override
    public LoginFailReason getReason(){
        return reason;
    }
}
