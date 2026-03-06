package com.dev.workhiveback.mappers.chat;

import com.dev.workhiveback.dtos.chat.ChatRoomPreviewDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    List<ChatRoomPreviewDto> selectMyRoomPreviewList(@Param("empId") String empId);

    ChatRoomPreviewDto selectRoomPreview(
            @Param("roomIndex") int roomIndex,
            @Param("empId") String empId
    );
}
