package com.dev.workhiveback.controllers.chat;

import com.dev.workhiveback.dtos.chat.ChatMessageDto;
import com.dev.workhiveback.services.chat.ChatMessageService;
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
public class ChatStompController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/send")
    public void send(ChatSendRequest request, Principal principal) {

        // 인증 실패
        if (principal == null || principal.getName() == null || principal.getName().isBlank()) {
            return;
        }

        String empId = principal.getName();
        int roomIndex = request.getRoomIndex();

        chatMessageService.sendAndPublish(roomIndex, empId, request.getMessage());

//        // 같은 방 구독자에게 브로드캐스트
//        String destination = "/topic/chat/room/" + roomIndex;
//        messagingTemplate.convertAndSend(destination, saved);
    }

    @Getter
    @Setter
    public static class ChatSendRequest {
        private int roomIndex;
        private String message;
    }

}
