package com.dev.workhiveback.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatUserMapper {
    int countByRoomIndexAndEmpId(@Param("roomIndex") int roomIndex,
                          @Param("empId") String empId);

    List<String> selectEmpIdsByRoomIndex(@Param("roomIndex") int roomIndex);
}
