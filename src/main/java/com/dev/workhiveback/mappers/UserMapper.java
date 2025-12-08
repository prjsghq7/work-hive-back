package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.entities.CodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<LoginDto> selectById(String id);

    int register(@Param(value = "id") String id,@Param(value = "password") String password);

    List<CodeEntity> selectTeamCodes();

    List<CodeEntity> selectUserStateCodes();
}
