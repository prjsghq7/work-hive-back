package com.dev.workhiveback.services.chat;

import com.dev.workhiveback.dtos.chat.ChatRoomPreviewDto;
import com.dev.workhiveback.mappers.chat.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;

    public List<ChatRoomPreviewDto> getMyRoomPreviewList(String empId) {
        return chatRoomMapper.selectMyRoomPreviewList(empId);
    }

    public ChatRoomPreviewDto getRoomPreview(int roomIndex, String empId) {
        return chatRoomMapper.selectRoomPreview(roomIndex, empId);
    }
}