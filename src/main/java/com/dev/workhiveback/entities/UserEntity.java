package com.dev.workhiveback.entities;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "index")
public class UserEntity {
    int index;
    int empId;
    int teamCode;
    int roleCode;
    int userState;

    String name;
    String phoneNumber;
    LocalDate birth;
    LocalDate startDate;
    String email;
    String password;
    float totalDayOffs;
    float remainingDayOffs;
    String picture;
}
