package com.dev.workhiveback.controllers.chat;

import com.dev.workhiveback.dtos.chat.ChatMessageDto;
import com.dev.workhiveback.services.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatMessageRestController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/messages")
    public List<ChatMessageDto> getMessages(
            @RequestParam int roomIndex,
            @RequestParam(required = false) Integer beforeIndex,
            Principal principal) {

        // 로그인 안됨
        if (principal == null || principal.getName() == null || principal.getName().isBlank()) {
            return null;
        }

        String empId = principal.getName();

        return chatMessageService.getMessages(roomIndex, beforeIndex, empId);
    }
}

