package com.dev.workhiveback.exceptions;

import com.dev.workhiveback.results.reasons.RegisterFailReason;
import lombok.Getter;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
public class RegisterException extends RuntimeException {

    private final RegisterFailReason reason;

    public RegisterException(RegisterFailReason reason,String message) {
        super(message);
        this.reason = reason;
    }
}
