package com.dev.workhiveback.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEditDto {
    int index;// 그냥 회원가입 할때 그 인덱스
    String empId;
    String name;

    int teamCode;
    int roleCode;
    int userState;

    String email;
    String phoneNumber;
    int totalDayOffs;

}
