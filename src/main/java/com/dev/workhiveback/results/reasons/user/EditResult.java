package com.dev.workhiveback.results.reasons.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditResult {
    private boolean result;
    public static EditResult success() {
        return new EditResult(true);
    }
}
