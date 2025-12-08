package com.dev.workhiveback.results;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResult<T> {
    //모든 API 응답 형식을 통일하면서도, data는 API마다 다르기 때문

    private boolean success; // 요청 성공 여부
    private T data;          // 실제 응답 데이터 (성공 시)
    private String code;     // 에러 코드 (실패 시)
    private String message;  // 메시지

    public static <T> CommonResult<T> success(T data) {
        //성공한 경우 data를 전송
        return new CommonResult<>(true, data, null, "success");
    }

    public static <T> CommonResult<T> fail(String code, String message) {
        //실패한 경우 dat는 없고 code와 message만 전송
        return new CommonResult<>(false, null, code, message);
    }
}
