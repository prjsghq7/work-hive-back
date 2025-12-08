package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.mappers.LeaveMapper;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {
    private final LeaveMapper leaveMapper;

    public LeaveService(LeaveMapper leaveMapper) {
        this.leaveMapper = leaveMapper;
    }

    public CalendarDto[] getCalendarData(/*UserEntity signedUser*/) {
        return this.leaveMapper.selectCalendarData(/*signedUser.getIndex()*/1);
    }
}
