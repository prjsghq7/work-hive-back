package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.dtos.UserDto;
import com.dev.workhiveback.exceptions.LoginException;
import com.dev.workhiveback.exceptions.RegisterException;
import com.dev.workhiveback.mappers.UserMapper;
import com.dev.workhiveback.results.reasons.LoginFailReason;
import com.dev.workhiveback.results.reasons.LoginResult;
import com.dev.workhiveback.results.reasons.RegisterFailReason;
import com.dev.workhiveback.results.reasons.RegisterResult;
import com.dev.workhiveback.security.TokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginResult login(LoginDto request) {

        LoginDto user = this.userMapper.selectById(request.getId())
                .orElseThrow(() -> new LoginException(LoginFailReason.USER_NOT_FOUND, "사용자를 찾을수 없습니다."));
        //orElseThrow()가 되는 이유 mapper에서 Optional로 받기 때문에 -> Optional은 데이터가 없을때 Option.empty()상태에서 .orElseThrow()를 호출하면 전달한 예외가 발생한다.
        //OPtional은 내부적으로 값이 있으면 꺼내고, 값이 없으면 에러를 던지는 메서드를 제공.
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) {
            throw new LoginException(LoginFailReason.WRONG_PASSWORD, "비밀번호가 틀립니다.");
        }
        String token = tokenProvider.createByEmpId(request.getId());
        System.out.println("로그인 성공");
        return new LoginResult(user.getId(),token);
    }

    public RegisterResult register(String id, String password) {
        if (id == null || password == null) {
            throw new RegisterException(RegisterFailReason.NOT_ENOUGH_INFO, "아이디,이름 혹은 비밀번호를 입력하지 않음.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        int result = this.userMapper.register(id, encodedPassword);

        if (result <= 0) {
            throw new RegisterException(RegisterFailReason.FAIL, "회원가입 실패");
        }
        return RegisterResult.success();
    }

    public LoginDto getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String empId = (String) authentication.getPrincipal();
        return userMapper.selectById(empId)
                .orElseThrow(() -> new LoginException(LoginFailReason.USER_NOT_FOUND, "사용자를 찾을수 없습니다."));
    }
}
