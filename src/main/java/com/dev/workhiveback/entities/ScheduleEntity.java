package com.dev.workhiveback.entities;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleEntity {
    private int index;
    private int state;
    private String title;
    private String detail;
    private LocalDate startDate;
    private LocalDate endDate;
}
