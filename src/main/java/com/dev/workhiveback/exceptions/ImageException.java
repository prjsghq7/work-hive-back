package com.dev.workhiveback.exceptions;

import com.dev.workhiveback.exceptions.user.ReasonedException;
import com.dev.workhiveback.results.reasons.image.ImageFailReason;
import com.dev.workhiveback.results.reasons.login.LoginFailReason;
import lombok.Getter;

@Getter
public class ImageException extends RuntimeException implements ReasonedException {
    private final ImageFailReason reason;

    public ImageException(ImageFailReason reason, String message) {
        super(message);
        this.reason = reason;
    }

    @Override
    public ImageFailReason getReason(){
        return reason;
    }
}
