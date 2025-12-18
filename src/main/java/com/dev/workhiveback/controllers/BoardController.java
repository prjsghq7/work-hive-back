package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.board.BoardNewDto;
import com.dev.workhiveback.entities.BoardEntity;
import com.dev.workhiveback.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public String postCreate(@RequestBody BoardNewDto dto) {
        boardService.create(dto);
        return "OK";
    }

    @GetMapping
    public List<BoardEntity> getBoards() {
        return boardService.findAll();
    }

    @GetMapping("/notice")
    public List<BoardEntity> getNoticeBoard() {
        return boardService.NoticeAll();
    }

    @GetMapping("/family-event")
    public List<BoardEntity> getFamilyEventBoard() {
        return boardService.FamilyEventAll();
    }

    @GetMapping("/{id}")
    public BoardEntity getDetail(@PathVariable int id) {
        return boardService.findById(id);
    }
}