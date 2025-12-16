package com.dev.workhiveback.dtos;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserDto {
    private String empId;
    private String password;
    private int teamCode;
    private int roleCode;
    private String name;
    private String phoneNumber;
    private LocalDate birth;
    private LocalDate startDate;
    private double totalDayOffs;
    private double remainingDayOffs;
}
