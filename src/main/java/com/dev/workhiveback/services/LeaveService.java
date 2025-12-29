package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.entities.LeaveEntity;
import com.dev.workhiveback.mappers.LeaveMapper;
import com.dev.workhiveback.results.reasons.calendars.CalendarResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {
    private final LeaveMapper leaveMapper;

    public LeaveService(LeaveMapper leaveMapper) {
        this.leaveMapper = leaveMapper;
    }

    public CalendarResult getCalendarData(/*UserEntity signedUser*/) {
        List<CalendarDto> calendarDtoList = leaveMapper.selectCalendarData(/*signedUser.getIndex()*/900);
        return new CalendarResult(calendarDtoList);// 임시로 900 넣어둠
    }

    public List<LeaveEntity> findAll() {
        return leaveMapper.selectLeaves();
    }
}
