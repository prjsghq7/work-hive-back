package com.dev.workhiveback.dtos.leave;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarDto {
    // 달력에 불러올 데이터 (연차 및 일정 공통)
    private int index;              // 연차/일정 index
    private String name;            // 이름 (연차용)
    private String title;           // 제목 (일정용)
    private String typeText;        // 연차 유형 또는 일정 상태 텍스트
    private LocalDate startDate;    // 시작일
    private LocalDate endDate;      // 종료일
    private String calendarType;    // 달력 타입 (dayOffs, schedules)
}
