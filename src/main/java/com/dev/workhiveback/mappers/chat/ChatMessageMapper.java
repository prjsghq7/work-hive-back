package com.dev.workhiveback.mappers.chat;

import com.dev.workhiveback.dtos.chat.ChatMessageDto;
import com.dev.workhiveback.entities.ChatMessageEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    int insert(ChatMessageEntity chatMessage);

    ChatMessageDto selectDtoByIndex(@Param("index") int index);

    List<ChatMessageDto> selectByRoomIndex(
            @Param("roomIndex") int roomIndex,
            @Param("beforeIndex") Integer beforeIndex,
            @Param("limit") int limit
    );
}
