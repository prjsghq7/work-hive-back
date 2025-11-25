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
    int emp_id;
    int team_code;
    int role_code;
    int user_state;

    String name;
    String phoneNumber;
    LocalDate birth;
    LocalDate start_date;
    String email;
    String password;
    float total_day_offs;
    float remaining_day_offs;
    String picture;
}
