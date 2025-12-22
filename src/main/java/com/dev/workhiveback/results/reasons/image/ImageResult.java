package com.dev.workhiveback.results.reasons.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageResult {
    private boolean result;

    public static ImageResult success() {
        return new ImageResult(true);
    }
}
