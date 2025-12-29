package com.dev.workhiveback.entities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveEntity {
    private int index;
    private String requesterId;
    private String approverId;
    private int state;
    private int type;
    private LocalDateTime requestDate;
    private LocalDateTime approveDate;
    private LocalDate startDate;
    private LocalDate endDate;
}
