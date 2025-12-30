package com.dev.workhiveback.exceptions;

import com.dev.workhiveback.exceptions.user.EditException;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.image.ImageFailReason;
import com.dev.workhiveback.results.reasons.login.LoginFailReason;
import com.dev.workhiveback.results.reasons.register.RegisterFailReason;
import com.dev.workhiveback.results.reasons.user.EditFailReason;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
//전역적으로 예외를 핸들링하고 일관된 JSON 응답을 제공하는 스프링의 강력한 기능.
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<CommonResult<Void>> handleLoginException(LoginException e) {
        //여기에서는 실패 응답만 만들고 있다. 그렇기 때문에 실패 응답의 data는 없다는 의미로 CommonResult<Void>를 사용한다.
        LoginFailReason reason = e.getReason();
//로그인에 실패한 이유를 가지고 오기위함.
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) //로그인 실패한 상태
                .body(CommonResult.fail(reason.name(), e.getMessage())); // 로그인 실패한 이유에 대해서 data에 js에서 처리할것과 같이 넘겨준다.
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<CommonResult<Void>> handleRegisterException(RegisterException e) {
        RegisterFailReason reason = e.getReason();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResult.fail(reason.name(), e.getMessage()));
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<CommonResult<Void>> handleImageException(ImageException e) {
        ImageFailReason reason = e.getReason();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResult.fail(reason.name(), e.getMessage()));
    }

    @ExceptionHandler(EditException.class)
    public ResponseEntity<CommonResult<Void>> handleEditException(EditException e) {
        EditFailReason reason = e.getReason();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResult.fail(reason.name(), e.getMessage()));
    }

}