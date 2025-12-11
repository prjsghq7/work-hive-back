package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.board.BoardNewDto;
import com.dev.workhiveback.entities.BoardEntity;
import com.dev.workhiveback.mappers.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public void create(BoardNewDto dto) {
        boardMapper.insertBoard(dto);
    }

    public List<BoardEntity> findAll() {
        return boardMapper.selectBoards();
    }

    public BoardEntity findById(int id) {
        return boardMapper.findById(id);
    }
}