package com.dev.workhiveback.regexes;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BoardRegex {

    // 제목: 1~30자, 개행 불가
    public static final String TITLE_REGEX = "^[^\\r\\n]{1,30}$";
}
