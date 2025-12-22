package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.dtos.user.UserEditDto;
import com.dev.workhiveback.dtos.user.UserSearchDto;
import com.dev.workhiveback.entities.CodeEntity;
import com.dev.workhiveback.dtos.UserDto;
import com.dev.workhiveback.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<UserDto> selectById(String empId);

    Optional<UserEntity> selectByIndex(int index);

    List<UserSearchDto> searchWithFilter(@Param(value = "name") String name,
                                         @Param(value = "teamCode") int teamCode,
                                         @Param(value = "userState") int userState);

    int selectCountByEmpId(@Param(value = "empId") int empId);

    int selectCountByEmail(@Param(value = "email") String email);

    int selectCountByPhoneNumber(@Param(value = "phoneNumber") String phoneNumber);

    int update(@Param(value = "user") UserEntity user);

    List<CodeEntity> selectTeamCodes();

    List<CodeEntity> selectUserStateCodes();

    List<CodeEntity> selectRoleCodes();

    UserEditDto selectUserForEdit(@Param(value = "index") int index);

    int register(UserEntity user);

    Optional<UserEntity> selectByEmpId(@Param("empId") String empId);
}
