package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.board.BoardNewDto;
import com.dev.workhiveback.entities.BoardEntity;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.exceptions.board.BoardException;
import com.dev.workhiveback.mappers.BoardMapper;
import com.dev.workhiveback.regexes.BoardRegex;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.board.BoardFailReason;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public void create(BoardNewDto dto) {
        if (!dto.getTitle().matches(BoardRegex.TITLE_REGEX) || dto.getTitle() == null) {
            throw new BoardException(
                    BoardFailReason.CREATE_INVALID,
                    "제목은 1자 ~ 30자까지만 입력할 수 있습니다."
            );
        }

        if (dto.getContent() == null || dto.getContent().length() == 0) {
            throw new BoardException(
                    BoardFailReason.CREATE_INVALID,
                    "내용을 입력해주세요."
            );
        }

        boardMapper.insertBoard(dto);
    }

    public int update(BoardNewDto dto, int id) {
        return boardMapper.updateBoard(dto, id);
    }

    public int deleteUpdate(UserEntity loginUser, int boardId) {

        // 1. 게시글 조회
        BoardEntity board = boardMapper.findById(boardId);
        if (board == null) {
            throw new BoardException(
                    BoardFailReason.NOT_FOUND,
                    "게시글이 존재하지 않습니다."
            );
        }

        // 2. 로그인 안 된 경우
        if (loginUser == null) {
            throw new BoardException(
                    BoardFailReason.NOT_LOGIN,
                    "로그인이 필요합니다."
            );
        }

        // 3. 작성자 검증 (핵심)
        if (!String.valueOf(loginUser.getEmpId())
                .equals(String.valueOf(board.getEmpId()))) {
            throw new BoardException(
                    BoardFailReason.NOT_LOGIN,
                    "삭제할 권한이 없습니다."
            );
        }

        // 4. 삭제 처리
        return boardMapper.deleteUpdateBoard(boardId);
    }

    public BoardEntity findById(int id) {
        boardMapper.increaseView(id);
        return boardMapper.findById(id);
    }

    public List<BoardEntity> findAll(int page, int size) {
        int offset = (page - 1) * size;
        return boardMapper.selectBoardsPaging(offset, size);
    }

    public int countAll() {
        return boardMapper.countBoards();
    }

    public List<BoardEntity> NoticeAll() {
        return boardMapper.selectNoticeBoards();
    }

    public List<BoardEntity> FamilyEventAll() {
        return boardMapper.selectFamilyEventBoards();
    }
}