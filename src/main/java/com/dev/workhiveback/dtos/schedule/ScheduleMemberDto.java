package com.dev.workhiveback.dtos.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleMemberDto {
    private String memberId;
    private Integer scheduleIndex;
    private Integer userTeamCode;
    private Boolean isManager;
}
