package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.board.BoardNewDto;
import com.dev.workhiveback.entities.BoardEntity;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.results.reasons.board.BoardResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    int insertBoard(@Param(value = "dto") BoardNewDto dto);

    int updateBoard(@Param(value = "dto") BoardNewDto dto,
                    @Param(value = "id") int id);

    int deleteUpdateBoard(@Param(value = "id")int id);

    List<BoardEntity> selectBoardsPaging(@Param(value = "offset") int offset,
                                         @Param(value = "size") int size);

    // ===== Notice 페이징 =====
    List<BoardEntity> selectNoticePaging(@Param("offset") int offset,
                                         @Param("size") int size);

    int countNotice();

    // ===== FamilyEvent 페이징 =====
    List<BoardEntity> selectFamilyEventPaging(@Param("offset") int offset,
                                              @Param("size") int size);

    int countFamilyEvent();

    int countBoards();

    List<BoardEntity> selectNoticeBoards();

    List<BoardEntity> selectFamilyEventBoards();

    BoardEntity findById(@Param(value = "id") int id);
    void increaseView(@Param(value = "id") int id);
}
