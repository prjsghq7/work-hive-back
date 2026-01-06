package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.dtos.leave.LeaveListDto;
import com.dev.workhiveback.entities.LeaveEntity;
import com.dev.workhiveback.mappers.LeaveMapper;
import com.dev.workhiveback.results.reasons.calendars.CalendarResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveService {
    private final LeaveMapper leaveMapper;

    public LeaveService(LeaveMapper leaveMapper) {
        this.leaveMapper = leaveMapper;
    }

    public CalendarResult getCalendarData(String filter, String empId, int teamCode) {
        List<CalendarDto> calendarDtoList = leaveMapper.selectCalendarData(filter, empId, teamCode);
        return new CalendarResult(calendarDtoList);
    }

    public List<LeaveListDto> findAll() {
        return leaveMapper.selectLeaveList();
    }

    public List<LeaveListDto> findByTab(String tab, String empId, int teamCode) {
        return leaveMapper.selectLeaveListByTab(tab, empId, teamCode);
    }

    public LeaveListDto findByIndex(int index) {
        return leaveMapper.selectLeaveByIndex(index);
    }

    public String patchAction(int index, String action, String empId) {
        LeaveListDto leave = leaveMapper.selectLeaveByIndex(index);
        if (leave == null) {
            throw new IllegalArgumentException("연차 정보를 찾을 수 없습니다.");
        }
        if (!leave.getApproverId().equals(empId)) {
            throw new IllegalArgumentException("처리 권한이 없습니다.");
        }

        int state;
        String message;
        
        if ("approve".equals(action)) {
            state = 3; // 승인
            message = "승인 완료";
        } else if ("reject".equals(action)) {
            state = 2; // 반려
            message = "반려 완료";
        } else {
            throw new IllegalArgumentException("잘못된 action 값입니다. (approve 또는 reject만 가능)");
        }

        leaveMapper.updateLeaveState(index, state, LocalDateTime.now());
        
        return message;
    }

    public void request(String empId, LeaveEntity leave) {
        if (leave.getStartDate() == null || leave.getEndDate() == null) {
            throw new IllegalArgumentException("시작일 또는 종료일이 null입니다.");
        }

        leave.setRequesterId(empId);
        leave.setState(1);
        leave.setRequestDate(LocalDateTime.now());
        leave.setApproveDate(LocalDateTime.now());
        leaveMapper.insertLeave(leave);
    }
}
