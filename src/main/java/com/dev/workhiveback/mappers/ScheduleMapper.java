package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.schedule.ScheduleMemberDto;
import com.dev.workhiveback.entities.ScheduleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScheduleMapper {

    void insertSchedule(ScheduleEntity schedule);

    void insertScheduleMember(ScheduleMemberDto scheduleMemberDto);
}
