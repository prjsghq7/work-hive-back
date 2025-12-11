package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.board.BoardNewDto;
import com.dev.workhiveback.entities.BoardEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    int insertBoard(BoardNewDto boardNewDto);

    List<BoardEntity> selectBoards();

    BoardEntity findById(int id);
}
