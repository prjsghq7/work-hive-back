package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.dtos.leave.LeaveListDto;
import com.dev.workhiveback.entities.LeaveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LeaveMapper {
    List<CalendarDto> selectCalendarData(@Param(value = "filter") String filter, @Param(value = "empId") String empId, @Param(value = "teamCode") int teamCode);

    List<LeaveListDto> selectLeaveList();

    List<LeaveListDto> selectLeaveListByTab(@Param(value = "tab") String tab, @Param(value = "empId") String empId, @Param(value = "teamCode") int teamCode);

    LeaveListDto selectLeaveByIndex(@Param(value = "index") int index);

    int updateLeaveState(@Param(value = "index") int index, @Param(value = "state") int state, @Param(value = "approveDate") java.time.LocalDateTime approveDate);

    int insertLeave(@Param(value="leave") LeaveEntity leaveEntity);
}
