package com.dev.workhiveback.controllers.chat;

import com.dev.workhiveback.dtos.chat.ChatRoomPreviewDto;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.services.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomRestController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/my")
    public List<ChatRoomPreviewDto> myRooms(@AuthenticationPrincipal UserEntity user) {
        return chatRoomService.getMyRoomPreviewList(user.getEmpId());
    }

    @GetMapping("/{roomIndex}/preview")
    public ChatRoomPreviewDto roomPreview(
            @PathVariable int roomIndex,
            @AuthenticationPrincipal UserEntity user) {
        return chatRoomService.getRoomPreview(roomIndex, user.getEmpId());
    }
}