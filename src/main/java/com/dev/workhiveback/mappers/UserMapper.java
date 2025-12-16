package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.dtos.user.UserSearchDto;
import com.dev.workhiveback.entities.CodeEntity;
import com.dev.workhiveback.dtos.UserDto;
import com.dev.workhiveback.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<UserDto> selectById(String empId);

    int register(@Param(value = "id") String id,@Param(value = "password") String password);

    List<UserSearchDto> searchWithFilter(@Param(value = "name") String name,
                                         @Param(value = "teamCode") int teamCode,
                                         @Param(value = "userState") int userState);

    List<CodeEntity> selectTeamCodes();

    List<CodeEntity> selectUserStateCodes();
    int register(UserEntity user);

    Optional<UserEntity> selectByEmpId(String empId);
}
