package com.dev.workhiveback.dtos.leave;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarDto {
    // 달력에 불러올 데이터
    private int index;              // 연차 index
    private String name;            // 이름
    private String typeText;        // day_offs_types 텍스트
    private LocalDate startDate;    // 시작일
    private LocalDate endDate;      // 종료일
    private String calendarType;    // 달력 타입
}
