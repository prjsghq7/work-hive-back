package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LeaveMapper {
    CalendarDto[] selectCalendarData(@Param(value = "userIndex") int userIndex);
}
