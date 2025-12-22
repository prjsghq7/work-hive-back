package com.dev.workhiveback.exceptions;

import com.dev.workhiveback.results.reasons.image.ImageFailReason;
import lombok.Getter;

@Getter
public class ImageException extends RuntimeException {
    private final ImageFailReason reason;

    public ImageException(ImageFailReason reason, String message) {
        super(message);
        this.reason = reason;
    }
}
