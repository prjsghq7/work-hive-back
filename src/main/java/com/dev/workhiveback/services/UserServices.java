package com.dev.workhiveback.services;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.dtos.UserDto;
import com.dev.workhiveback.dtos.user.UserDetailDto;
import com.dev.workhiveback.dtos.user.UserEditDto;
import com.dev.workhiveback.dtos.user.UserSearchDto;
import com.dev.workhiveback.entities.CodeEntity;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.exceptions.LoginException;
import com.dev.workhiveback.exceptions.RegisterException;
import com.dev.workhiveback.exceptions.user.EditException;
import com.dev.workhiveback.mappers.UserMapper;
import com.dev.workhiveback.results.reasons.*;
import com.dev.workhiveback.results.reasons.login.LoginFailReason;
import com.dev.workhiveback.results.reasons.login.LoginResult;
import com.dev.workhiveback.results.reasons.register.RegisterFailReason;
import com.dev.workhiveback.results.reasons.register.RegisterResult;
import com.dev.workhiveback.results.reasons.user.EditFailReason;
import com.dev.workhiveback.results.reasons.user.EditResult;
import com.dev.workhiveback.results.reasons.user.SearchResult;
import com.dev.workhiveback.security.TokenProvider;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private static final int DEFAULT_ROLE_CODE = 999;

    public LoginResult login(LoginDto request) {

        UserDto user = this.userMapper.selectById(request.getEmpId())
                .orElseThrow(() -> new LoginException(LoginFailReason.USER_NOT_FOUND, "사용자를 찾을수 없습니다."));
        //orElseThrow()가 되는 이유 mapper에서 Optional로 받기 때문에 -> Optional은 데이터가 없을때 Option.empty()상태에서 .orElseThrow()를 호출하면 전달한 예외가 발생한다.
        //OPtional은 내부적으로 값이 있으면 꺼내고, 값이 없으면 에러를 던지는 메서드를 제공.
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) {
            throw new LoginException(LoginFailReason.WRONG_PASSWORD, "비밀번호가 틀립니다.");
        }
        String token = tokenProvider.createByEmpId(request.getEmpId());
        System.out.println("로그인 성공");
        return new LoginResult(user.getEmpId(), token);
    }

    public RegisterResult register(UserEntity user) {
        if (user == null) {
            throw new RegisterException(RegisterFailReason.FAIL, "다시 시도해주세요.");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            throw new RegisterException(RegisterFailReason.NOT_ENOUGH_INFO, "이름 정보가 충분하지 않습니다.");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            throw new RegisterException(RegisterFailReason.NOT_ENOUGH_INFO, "이름 정보가 충분하지 않습니다.");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new RegisterException(RegisterFailReason.NOT_ENOUGH_INFO, "비밀번호 정보가 충분하지 않습니다.");
        }

        if (user.getBirth() == null) {
            throw new RegisterException(RegisterFailReason.NOT_ENOUGH_INFO, "생일 정보가 충분하지 않습니다.");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new RegisterException(RegisterFailReason.NOT_ENOUGH_INFO, "이메일 정보가 충분하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setStartDate(LocalDate.now());
        user.setRoleCode(DEFAULT_ROLE_CODE);
        user.setUserState(1);
        user.setRemainingDayOffs(0);
        user.setTotalDayOffs(0);
        int result = this.userMapper.register(user);

        if (result <= 0) {
            throw new RegisterException(RegisterFailReason.REGISTER_FAIL, "회원가입 실패");
        }
        return RegisterResult.success();
    }
//
//    public UserDto getMyProfile(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String empId = (String) authentication.getPrincipal();
//        return userMapper.selectById(empId)
//                .orElseThrow(() -> new LoginException(LoginFailReason.USER_NOT_FOUND, "사용자를 찾을수 없습니다."));
//    }

    public SearchResult search(String name, int teamCode, int userState) {
        List<UserSearchDto> userList = userMapper.searchWithFilter(name, teamCode, userState);
        return new SearchResult(userList);
    }

    public CodeResult getTeamCodes() {
        List<CodeEntity> teamCodes = userMapper.selectTeamCodes();
        boolean result = teamCodes.isEmpty();
        return new CodeResult(result, teamCodes);
    }

    public CodeResult getUserStateCodes() {
        List<CodeEntity> userStateCodes = userMapper.selectUserStateCodes();
        boolean result = userStateCodes.isEmpty();
        return new CodeResult(result, userStateCodes);
    }

    public CodeResult getRoleCodes() {
        List<CodeEntity> userStateCodes = userMapper.selectRoleCodes();
        boolean result = userStateCodes.isEmpty();
        return new CodeResult(result, userStateCodes);
    }

    public UserDetailDto getUserDetailByIndex(int index) {
        return userMapper.selectUserForDetail(index);
    }

    public UserEditDto getUserByIndex(int index) {
        return userMapper.selectUserForEdit(index);
    }

    public UserEntity getUserByEmpId(String empId) {
        return userMapper.selectByEmpId(empId)
                .orElseThrow(() -> new LoginException(LoginFailReason.USER_NOT_FOUND, "사번이 없다."));
    }

    ;

    public EditResult updateUser(UserEditDto user, MultipartFile profile) throws IOException {
        UserEntity dbUser = this.userMapper.selectByIndex(user.getIndex())
                .orElseThrow(() -> new EditException(EditFailReason.NOT_FOUND, "사용자를 찾을수 없습니다."));

        if (this.userMapper.selectCountByEmpId(user.getEmpId()) > 1) {
            throw new EditException(
                    EditFailReason.DUPLICATE_EMP_ID,
                    "중복된 사번 입니다."
            );
        }
        if (this.userMapper.selectCountByEmail(user.getEmail()) > 1) {
            throw new EditException(
                    EditFailReason.DUPLICATE_EMAIL,
                    "중복된 이메일 입니다."
            );
        }
        if (this.userMapper.selectCountByPhoneNumber(user.getPhoneNumber()) > 1) {
            throw new EditException(
                    EditFailReason.DUPLICATE_PHONE,
                    "중복된 전화번호 입니다."
            );
        }

        dbUser.setName(user.getName());
        dbUser.setTeamCode(user.getTeamCode());
        dbUser.setRoleCode(user.getRoleCode());
        dbUser.setUserState(user.getUserState());
        dbUser.setEmail(user.getEmail());
        dbUser.setPhoneNumber(user.getPhoneNumber());
        dbUser.setTotalDayOffs(user.getTotalDayOffs());
        if (profile != null) {
            dbUser.setProfile(profile.getBytes());
        }

        int result = this.userMapper.update(dbUser);
        if (result <= 0) {
            throw new EditException(
                    EditFailReason.EDIT_FAILED,
                    "수정에 실패 하였습니다."
            );
        }
        return EditResult.success();
    }

    public EditResult updateUserInfo(int userIndex, UserEditDto dto, MultipartFile profile) throws IOException {
        if (this.userMapper.updateUserInfo(userIndex, dto.getName(), dto.getPhoneNumber(), dto.getEmail()) == 0) {
            throw new EditException(EditFailReason.EDIT_FAILED, "업로드 실패");
        }
        if (profile != null && !profile.isEmpty()) {
            String contentType = profile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new EditException(EditFailReason.EDIT_FAILED, "이미지 파일만 업로드");
            }
            byte[] bytes = profile.getBytes();
            this.userMapper.updateProfile(userIndex, bytes, contentType);
        }
        return EditResult.success();
    }
}
