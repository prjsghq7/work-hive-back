package com.dev.workhiveback.services;

import com.dev.workhiveback.entities.ChatMessageEntity;
import com.dev.workhiveback.mappers.ChatMessageMapper;
import com.dev.workhiveback.mappers.ChatRoomMapper;
import com.dev.workhiveback.mappers.ChatUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomMapper chatRoomMapper;
    private final ChatUserMapper chatUserMapper;
    private final ChatMessageMapper chatMessageMapper;

    @Transactional
    public ChatMessageEntity sendMessage(int roomIndex, String empId, String message) {

        // 로그인 정보 없음 (Principal 없음 또는 토큰 문제)
        if (empId == null || empId.isBlank()) {
            return null;
        }

        //유효하지 않은 채팅방 번호
        if (roomIndex <= 0) {
            return null;
        }

        // 잘못된 요청
        if (message == null) {
            return null;
        }

        //TODO : Regex 처리

        // 해당 채팅방에 속한 사용자가 아님
        if (chatUserMapper.countByRoomIndexAndEmpId(roomIndex, empId) < 1) {
            return null;
        }

        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setRoomIndex(roomIndex);
        entity.setSender(empId);
        entity.setMessage(message);
        entity.setCreatedAt(LocalDateTime.now());

        // DB insert 실패
        if (chatMessageMapper.insert(entity) != 1) {
            return null;
        }

        return entity;
    }
}
