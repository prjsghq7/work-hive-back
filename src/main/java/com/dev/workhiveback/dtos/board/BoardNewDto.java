package com.dev.workhiveback.dtos.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardNewDto {
    private int empId;
    private int type;
    private String title;
    private String content;
}
