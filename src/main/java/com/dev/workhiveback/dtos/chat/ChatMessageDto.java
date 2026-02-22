package com.dev.workhiveback.dtos.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private int index;
    private int roomIndex;
    private String message;
    private String createdAt;

    private boolean mine;

    private String senderEmpId;
    private String senderName;
    private String senderTeamName;
    private String senderRoleName;
    private String senderImageUrl;
}
