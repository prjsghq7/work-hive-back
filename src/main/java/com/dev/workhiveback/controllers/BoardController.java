package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.board.BoardNewDto;
import com.dev.workhiveback.entities.BoardEntity;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.board.BoardResult;
import com.dev.workhiveback.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult<BoardResult>> getBoard() {
        List<BoardEntity> boards = boardService.findAll();

        BoardResult result = BoardResult.builder()
                .boards(boards)
                .build();

        return ResponseEntity.ok(CommonResult.success(result));
    }

    @RequestMapping(value = "/notice", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult<BoardResult>> getNoticeBoard() {
        List<BoardEntity> boards = boardService.NoticeAll();

        BoardResult result = BoardResult.builder()
                .boards(boards)
                .build();
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @RequestMapping(value = "/family-event", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult<BoardResult>> getFamilyEventBoard() {
        List<BoardEntity> boards = boardService.FamilyEventAll();

        BoardResult result = BoardResult.builder()
                .boards(boards)
                .build();
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult<BoardResult>> getDetail(
            @PathVariable("id") int id) {
        BoardEntity board = boardService.findById(id);

        BoardResult result = BoardResult.builder()
                .board(board)
                .build();

        return ResponseEntity.ok(CommonResult.success(result));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommonResult<String>> postBoardCreate(@RequestBody BoardNewDto dto) {

        boardService.create(dto);

        return ResponseEntity.ok(CommonResult.success("게시글 등록 완료"));
    }

    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public ResponseEntity<CommonResult<String>> patchDeleteBoard(@PathVariable("id") int id) {

        int deleteUpdate = boardService.deleteUpdate(id);

        if (deleteUpdate > 0) {
            return ResponseEntity.ok(CommonResult.success("게시글 삭제 성공!"));
        } else {
            return ResponseEntity.badRequest().body(CommonResult.fail("삭제 실패", "삭제 실패"));
        }
    };

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommonResult<String>> patchModifyBoard(@RequestBody BoardNewDto dto,
                                                                 @PathVariable int id) {
        int affected = boardService.update(dto, id);

        if (affected > 0) {
            return ResponseEntity.ok(CommonResult.success("게시글 수정 완료"));
        } else {
            return ResponseEntity.badRequest()
                    .body(CommonResult.fail("수정실패","수정실패"));
        }
    }

}