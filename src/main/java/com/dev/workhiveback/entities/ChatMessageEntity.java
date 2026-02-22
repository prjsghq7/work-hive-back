package com.dev.workhiveback.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageEntity {
    private int index;
    private int roomIndex;
    private String sender;
    private String message;
    private LocalDateTime createdAt;
}
