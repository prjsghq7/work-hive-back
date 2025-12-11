package com.dev.workhiveback.exceptions;

import com.dev.workhiveback.results.reasons.RegisterFailReason;
import lombok.Getter;

@Getter
public class RegisterException extends RuntimeException {

    private final RegisterFailReason reason;

    public RegisterException(RegisterFailReason reason,String message) {
        super(message);
        this.reason = reason;
    }
}
