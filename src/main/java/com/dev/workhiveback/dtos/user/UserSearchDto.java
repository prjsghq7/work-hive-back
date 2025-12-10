package com.dev.workhiveback.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSearchDto {
    int index;
    int empId;

    String name;
    String teamName;
    String roleName;
    String email;
    String userStateName;
}
