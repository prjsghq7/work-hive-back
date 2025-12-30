package com.dev.workhiveback.regexes;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserRegex {
    // 사번번호 : 8~12자리의 숫자 (0으로 시작 가능)
    public static final Regex empId = new Regex("^\\d{8,12}$");

    // 이름: 한글 2~25자
    public static final Regex _name = new Regex("^[가-힣]{2,25}$");

    // 전화번호 : 숫자(3)-숫자(3~4)-숫자(4)
    public static final Regex phoneNumber = new Regex("^\\d{3}-\\d{3,4}-\\d{4}$");

    // 이메일: 영문·숫자·특수문자(. _ -) 사용, @ 포함, 공백 불가, 50자 이내
    public static final Regex email = new Regex("^(?=.{1,50}$)[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$");

    // 생년월일: YYYY-MM-DD 형식 (1900~2099년 범위)
    public static final Regex birth = new Regex("^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");

    // 입사일: YYYY-MM-DD 형식 (2015~2099년 범위)
    public static final Regex startDate = new Regex("^(201[5-9]|20[2-9]\\d)-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");

    // 패스워드: 8~20자리
    public static final Regex password = new Regex("^[\\da-zA-Z`~!@#$%^&*()\\-_=+\\[\\]{}\\\\|;:'\",<.>/?]{8,20}$");
}
