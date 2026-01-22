package com.dev.workhiveback.mappers;

import com.dev.workhiveback.entities.ChatMessageEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    int insert(ChatMessageEntity chatMessage);
}
