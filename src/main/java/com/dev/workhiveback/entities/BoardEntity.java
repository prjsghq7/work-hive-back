package com.dev.workhiveback.entities;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEntity {

    private int index;
    private int empId;
    private int type;
    private String title;
    private String content;
    private int view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;
    private String name;
}