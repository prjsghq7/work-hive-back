package com.dev.workhiveback.controllers;

import com.dev.workhiveback.entities.ChatMessageEntity;
import com.dev.workhiveback.services.ChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@MessageMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatService chatService;

    @MessageMapping("/send")
    public void send(ChatSendRequest request, Principal principal) {

        // 인증 실패
        if (principal == null || principal.getName() == null || principal.getName().isBlank()) {
            return;
        }

        String empId = principal.getName();
        int roomIndex = request.getRoomIndex();

        ChatMessageEntity saved = chatService.sendMessage(roomIndex, empId, request.getMessage());
        if (saved == null) {
            return;
        }

        // 같은 방 구독자에게 브로드캐스트
        String destination = "/topic/chat/room/" + roomIndex;
        messagingTemplate.convertAndSend(destination, saved);
    }

    @Getter
    @Setter
    public static class ChatSendRequest {
        private int roomIndex;
        private String message;
    }
}
