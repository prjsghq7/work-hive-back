package com.dev.workhiveback.dtos.leave;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveStatsDto {
    private int pendingApprovalCount;  // 승인 대기 (내가 요청자인데 대기 중)
    private int pendingApproverCount;  // 결재 대기 (내가 승인자인데 대기 중)
}
