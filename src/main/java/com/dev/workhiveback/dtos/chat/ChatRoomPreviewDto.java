package com.dev.workhiveback.dtos.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomPreviewDto {
    private int roomIndex;
    private String title;

    private String lastMessage;
    private LocalDateTime lastMessageAt;

    private int unreadCount;
}
