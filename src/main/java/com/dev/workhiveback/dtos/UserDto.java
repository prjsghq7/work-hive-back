package com.dev.workhiveback.dtos;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserDto {
    private String emp_id;
    private String password;
    private int team_code;
    private int role_code;
    private String name;
    private String phone_number;
    private LocalDate birth;
    private LocalDate start_date;
    private double total_day_offs;
    private double remaining_day_offs;
}
