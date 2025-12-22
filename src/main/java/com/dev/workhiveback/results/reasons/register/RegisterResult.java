package com.dev.workhiveback.results.reasons.register;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResult {
    private boolean result;

    public static RegisterResult success() {
        return new RegisterResult(true);
    }
}
