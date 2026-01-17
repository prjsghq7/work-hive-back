package com.dev.workhiveback.dtos.leave;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveListDto {
    // 연차 내역에 사용할 리스트 데이터
    private int index;
    private int teamCode;
    private int roleCode;
    private String requesterId;
    private String requesterName;
    private String requesterTeamName;
    private String approverId;
    private String approverName;
    private String approverTeamName;
    private String approverRoleName;
    private String approverProfileImg;
    private String stateText;
    private String typeText;
    private String reason;
    private LocalDateTime requestDate;
    private LocalDateTime approveDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String calendarType;    // 달력 타입
}
