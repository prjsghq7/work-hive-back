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
    int index;
    int empId;
    String name;

    int teamCode;
    int roleCode;
    int userState;

    String email;
    String phoneNumber;
    int totalDayOffs;
}
