package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.dtos.UserDto;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.user.SearchResult;
import com.dev.workhiveback.results.reasons.CodeResult;
import com.dev.workhiveback.results.reasons.LoginResult;
import com.dev.workhiveback.results.reasons.RegisterResult;
import com.dev.workhiveback.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserServices userServices;

    @PostMapping(value = "/login")
    public ResponseEntity<CommonResult<LoginResult>> login(@RequestBody LoginDto request) {
        LoginResult result = this.userServices.login(request);
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<CommonResult<RegisterResult>> register(@RequestBody UserEntity user) {
        RegisterResult result = userServices.register(user);
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @PostMapping(value = "/search")
    public ResponseEntity<CommonResult<SearchResult>> search(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int teamCode,
            @RequestParam(defaultValue = "0") int userState) {
        SearchResult result = userServices.search(name, teamCode, userState);
        result.getUserList().forEach(user -> {
            System.out.println(user.getName());
        });
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @GetMapping(value = "/team-list")
    public ResponseEntity<CommonResult<CodeResult>> getTeamList() {
        CodeResult result = userServices.getTeamCodes();
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @GetMapping(value = "/state-list")
    public ResponseEntity<CommonResult<CodeResult>> getUserStateList() {
        CodeResult result = userServices.getUserStateCodes();
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @GetMapping("/me")
    public UserEntity me(@AuthenticationPrincipal UserEntity user) {
        return user;  // 현재 로그인한 유저 정보 그대로 리턴
    }
//---------------- 사용법-----------------------------
    //로그인한 사람(ROLE_USER,ROLE_ADMIN상관없이)
    @GetMapping("/my-page")
    @PreAuthorize("isAuthenticated()")   // 로그인만 되어있으면 OK -> 되는 이유는 @EnableMethodSecurity를 websecurityconfig에 추가해서
    public String myPage() {
        return "내 정보 페이지";
    }

    //일반 사용자만 (Admin제외하고)
    @GetMapping("/user-only")
    @PreAuthorize("hasRole('USER') and !hasRole('ADMIN')")
    public String userOnly() {
        return "일반 유저 전용 페이지";
    }

    //관리자만 접근 가능
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')") // ROLE_ADMIN 가진 사람만
    public String adminPage() {
        return "관리자 전용 페이지";
    }
}