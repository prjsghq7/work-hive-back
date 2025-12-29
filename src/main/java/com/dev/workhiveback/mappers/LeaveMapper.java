package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.entities.LeaveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LeaveMapper {
    List<CalendarDto> selectCalendarData(@Param(value = "empId") String empId);

    List<LeaveEntity> selectLeaves();

    int insertLeave(@Param(value="leave") LeaveEntity leaveEntity);
}
