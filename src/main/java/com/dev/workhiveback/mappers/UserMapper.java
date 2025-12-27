package com.dev.workhiveback.mappers;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.dtos.user.UserDetailDto;
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

    int selectCountByEmpId(@Param(value = "empId") String empId);

    int selectCountByEmail(@Param(value = "email") String email);

    int selectCountByPhoneNumber(@Param(value = "phoneNumber") String phoneNumber);

    //userInfo 변경하는 mapper
    int update(@Param(value = "user") UserEntity user);

    int updateUserInfo(@Param("index") int index,
                       @Param("name") String name,
                       @Param("phoneNumber") String phoneNumber,
                       @Param("email") String email);

    int updateProfile(@Param("index") int index,
                      @Param("profile") byte[] profile,
                      @Param("contentType") String contentType);

    List<CodeEntity> selectTeamCodes();

    List<CodeEntity> selectUserStateCodes();

    List<CodeEntity> selectRoleCodes();

    UserDetailDto selectUserForDetail(@Param(value = "index") int index);

    UserEditDto selectUserForEdit(@Param(value = "index") int index);

    int register(UserEntity user);

    Optional<UserEntity> selectByEmpId(@Param("empId") String empId);
}
