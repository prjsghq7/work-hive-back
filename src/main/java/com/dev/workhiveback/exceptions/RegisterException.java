package com.dev.workhiveback.exceptions;

import com.dev.workhiveback.exceptions.user.ReasonedException;
import com.dev.workhiveback.results.reasons.login.LoginFailReason;
import com.dev.workhiveback.results.reasons.register.RegisterFailReason;
import lombok.Getter;

@Getter
public class RegisterException extends RuntimeException implements ReasonedException {

    private final RegisterFailReason reason;

    public RegisterException(RegisterFailReason reason,String message) {
        super(message);
        this.reason = reason;
    }
    @Override
    public RegisterFailReason getReason(){
        return reason;
    }
}
