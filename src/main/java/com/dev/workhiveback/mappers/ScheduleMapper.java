package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.dtos.schedule.ScheduleMemberDto;
import com.dev.workhiveback.entities.ScheduleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    List<CalendarDto> selectCalendarData(@Param("filter") String filter, @Param("empId") String empId, @Param("teamCode") int teamCode);

    void insertSchedule(ScheduleEntity schedule);

    void insertScheduleMember(ScheduleMemberDto scheduleMemberDto);
}
