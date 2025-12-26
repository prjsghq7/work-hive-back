package com.dev.workhiveback.entities;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "index")
public class UserEntity {
    private int index;
    private String empId;
    private int teamCode;
    private int roleCode;
    private int userState;

    private String name;
    private String phoneNumber;
    private LocalDate birth;
    private LocalDate startDate;
    private String email;
    private String password;
    private float totalDayOffs;
    private float remainingDayOffs;
    private byte[] profile;
    private String profileContentType;
}
