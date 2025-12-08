package com.dev.workhiveback.dtos.leave;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveListDto {
    // 연차 내역에 사용할 리스트 데이터
    
    // 임시
    private int index;              // 연차 index
    private String typeText;        // day_offs_types 텍스트
    private LocalDate startDate;    // 시작일
    private LocalDate endDate;      // 종료일
    private String calendarType;    // 달력 타입
}
