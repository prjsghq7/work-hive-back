package com.dev.workhiveback.exceptions.user;


import com.dev.workhiveback.results.reasons.user.EditFailReason;
import lombok.Getter;

@Getter
public class EditException extends RuntimeException{
    private final EditFailReason reason;

    public EditException(EditFailReason reason, String message) {
        super(message);
        this.reason = reason;
    }
}
