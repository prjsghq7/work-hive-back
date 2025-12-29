package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.leave.CalendarDto;
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

    public CalendarResult getCalendarData(String empId) {
        List<CalendarDto> calendarDtoList = leaveMapper.selectCalendarData(empId);
        return new CalendarResult(calendarDtoList);// 임시로 900 넣어둠
    }

    public List<LeaveEntity> findAll() {
        return leaveMapper.selectLeaves();
    }

    public void request(String empId, LeaveEntity leave) {
        System.out.println("받은 데이터 - approverId: " + leave.getApproverId());
        System.out.println("받은 데이터 - type: " + leave.getType());
        System.out.println("받은 데이터 - startDate: " + leave.getStartDate());
        System.out.println("받은 데이터 - endDate: " + leave.getEndDate());

        if (leave.getStartDate() == null || leave.getEndDate() == null) {
            throw new IllegalArgumentException("시작일 또는 종료일이 null입니다.");
        }

        leave.setRequesterId(empId);
        leave.setState(1);
        leave.setRequestDate(LocalDateTime.now());
        leave.setApproveDate(LocalDateTime.now()); // 추후 NOT NULL -> NULL로 변경 필요
        leaveMapper.insertLeave(leave);
    }

    /*
    public Result insert(UserEntity signedUser, ReviewEntity review) {
        if (UserService.isInvalidUser(signedUser)) {
            if (signedUser == null || signedUser.isDeleted()) {
                return CommonResult.FAILURE_SESSION_EXPIRED;
            }
            return CommonResult.FAILURE_SUSPENDED;
        }

        if (this.reviewMapper.selectCountByUserIdAndBookId(signedUser.getId(), review.getBookId()) > 0) {
            return CommonResult.FAILURE_DUPLICATE;
        }

        if (review.getScope() < 1 || review.getScope() > 5
                || review.getComment() == null || review.getComment().isEmpty()) {
            return CommonResult.FAILURE;
        }

        review.setUserId(signedUser.getId());
        review.setCreatedAt(LocalDateTime.now());
        review.setModifiedAt(LocalDateTime.now());
        review.setDeleted(false);

        return this.reviewMapper.insert(review) > 0 ?
                CommonResult.SUCCESS :
                CommonResult.FAILURE;
    }*/
}
