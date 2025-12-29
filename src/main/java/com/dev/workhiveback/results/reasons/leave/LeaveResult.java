package com.dev.workhiveback.results.reasons.leave;

import com.dev.workhiveback.entities.LeaveEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class LeaveResult {
    private List<LeaveEntity> leaves;
    private LeaveEntity leave;
}
