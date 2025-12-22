package com.dev.workhiveback.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "index")
public class ImageEntity {
    private int index;
    private String name;
    private String contentType;
    private LocalDateTime createdAt;
    private byte[] data;
}
