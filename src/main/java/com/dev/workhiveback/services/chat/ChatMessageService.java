package com.dev.workhiveback.services.chat;

import com.dev.workhiveback.dtos.chat.ChatMessageDto;
import com.dev.workhiveback.dtos.chat.ChatNotifyDto;
import com.dev.workhiveback.entities.ChatMessageEntity;
import com.dev.workhiveback.mappers.chat.ChatMessageMapper;
import com.dev.workhiveback.mappers.chat.ChatRoomMapper;
import com.dev.workhiveback.mappers.ChatUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private static final int CHAT_MESSAGE_LIMIT = 30;

    private final ChatRoomMapper chatRoomMapper;
    private final ChatUserMapper chatUserMapper;
    private final ChatMessageMapper chatMessageMapper;

    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void sendAndPublish(int roomIndex, String empId, String message) {

        ChatMessageDto saved = sendMessage(roomIndex, empId, message);
        if (saved == null) {
            return;
        }

        // 1) 같은 방 구독자에게 브로드캐스트
        broadcastMessage(roomIndex, saved);

        // 2) 방 멤버 전체에게 개인 알림
        notifyRoomMembers(roomIndex, empId);
    }

    @Transactional
    public ChatMessageDto sendMessage(int roomIndex, String empId, String message) {

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

        // 핵심: insert 결과로 생성된 PK(index)로 DTO 재조회
        if (entity.getIndex() <= 0) {
            return null;
        }

        ChatMessageDto dto = chatMessageMapper.selectDtoByIndex(entity.getIndex());
        if (dto == null) {
            return null;
        }

        dto.setMine(empId.equals(dto.getSenderEmpId()));
        return dto;
    }

    private void broadcastMessage(int roomIndex, ChatMessageDto saved) {
        messagingTemplate.convertAndSend("/topic/chat/room/" + roomIndex, saved);
    }

    private void notifyRoomMembers(int roomIndex, String senderEmpId) {

        List<String> memberEmpIds = chatUserMapper.selectEmpIdsByRoomIndex(roomIndex);
        if (memberEmpIds == null || memberEmpIds.isEmpty()) {
            return;
        }

        ChatNotifyDto payload = new ChatNotifyDto(roomIndex);

        for (String targetEmpId : memberEmpIds) {
            if (targetEmpId == null || targetEmpId.isBlank()) continue;

            // 보낸 사람 제외
            if (senderEmpId.equals(targetEmpId)) continue;

            messagingTemplate.convertAndSendToUser(
                    targetEmpId,
                    "/queue/chat-notify",
                    payload
            );
        }
    }

    public List<ChatMessageDto> getMessages(int roomIndex,
                                            Integer beforeIndex,
                                            String myEmpId) {
        if (myEmpId == null || myEmpId.isBlank()) {
            return null;
        }

        if (roomIndex <= 0) {
            return null;
        }

        List<ChatMessageDto> messages = chatMessageMapper.selectByRoomIndex(roomIndex, beforeIndex, CHAT_MESSAGE_LIMIT);
        if (messages == null || messages.isEmpty()) {
            return List.of();
        }

        Collections.reverse(messages);

        for (ChatMessageDto dto : messages) {
            dto.setMine(myEmpId.equals(dto.getSenderEmpId()));
        }

        return messages;
    }
}
