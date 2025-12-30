package com.dev.workhiveback.results.reasons.user;

public enum EditFailReason {
    NOT_FOUND,              // 대상 사용자 없음
    INVALID_REQUEST,        // 요청 데이터 오류

    DUPLICATE_EMP_ID,       // 사번 중복
    INVALID_EMP_ID_FORMAT,  // 사번 형식 오류

    DUPLICATE_EMAIL,        // 이메일 중복
    INVALID_EMAIL_FORMAT,   // 이메일 형식 오류

    DUPLICATE_PHONE,        // 전화번호 중복
    INVALID_PHONE_NUMBER_FORMAT,    // 전화번호 형식 오류

    UNAUTHORIZED,           // 권한 없음
    EDIT_FAILED,            // 수정 실패
}
