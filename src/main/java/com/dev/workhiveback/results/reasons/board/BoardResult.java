package com.dev.workhiveback.results.reasons.board;

import com.dev.workhiveback.entities.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardResult {
    private List<BoardEntity> boards;
    private BoardEntity board;
}
