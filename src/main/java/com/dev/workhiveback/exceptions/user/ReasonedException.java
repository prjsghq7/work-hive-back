package com.dev.workhiveback.exceptions.user;

public interface ReasonedException {
    Enum<?> getReason();

    String getMessage();
}
