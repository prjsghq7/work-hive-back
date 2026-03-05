package com.dev.workhiveback.dtos.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleRegisterDto {
    private Integer state;
    private String title;
    private String detail;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ScheduleMemberDto> members;
}
