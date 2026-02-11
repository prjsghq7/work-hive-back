package com.dev.workhiveback.exceptions.board;

import com.dev.workhiveback.exceptions.user.ReasonedException;
import com.dev.workhiveback.results.reasons.board.BoardFailReason;
import lombok.Getter;

@Getter
public class BoardException extends RuntimeException implements ReasonedException {
    private final BoardFailReason reason;

    public BoardException(BoardFailReason reason, String message) {
        super(message);
        this.reason = reason;
    }
}
