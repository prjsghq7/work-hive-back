package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.dtos.schedule.ScheduleMemberDto;
import com.dev.workhiveback.dtos.schedule.ScheduleRegisterDto;
import com.dev.workhiveback.entities.ScheduleEntity;
import com.dev.workhiveback.mappers.ScheduleMapper;
import com.dev.workhiveback.results.reasons.calendars.CalendarResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    public CalendarResult getCalendarData(String filter, String empId, int teamCode) {
        List<CalendarDto> calendarDtoList = scheduleMapper.selectCalendarData(filter, empId, teamCode);
        return new CalendarResult(calendarDtoList);
    }

    public void register(ScheduleRegisterDto register) {
        if (register.getStartDate() == null || register.getEndDate() == null) {
            throw new IllegalArgumentException("시작일 또는 종료일이 null입니다.");
        }

        ScheduleEntity schedule = ScheduleEntity.builder()
                .title(register.getTitle())
                .detail(register.getDetail())
                .startDate(register.getStartDate())
                .endDate(register.getEndDate())
                .state(register.getState() != null ? register.getState() : 1)
                .build();

        scheduleMapper.insertSchedule(schedule);
        int scheduleIndex = schedule.getIndex();

        List<ScheduleMemberDto> members = register.getMembers();
        if (members != null) {
            for (ScheduleMemberDto m : members) {
                System.out.println(m.getUserTeamCode());
                m.setScheduleIndex(scheduleIndex);
                scheduleMapper.insertScheduleMember(m);
            }
        }
    }
}
