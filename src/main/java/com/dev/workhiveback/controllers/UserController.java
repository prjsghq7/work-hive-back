package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.dtos.user.UserEditDto;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.user.EditResult;
import com.dev.workhiveback.results.reasons.user.SearchResult;
import com.dev.workhiveback.results.reasons.CodeResult;
import com.dev.workhiveback.results.reasons.login.LoginResult;
import com.dev.workhiveback.results.reasons.register.RegisterResult;
import com.dev.workhiveback.services.UserServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;

@Slf4j
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

    @GetMapping(value = "/role-list")
    public ResponseEntity<CommonResult<CodeResult>> getRoleList() {
        CodeResult result = userServices.getRoleCodes();
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @GetMapping(value = "/info")
    public ResponseEntity<CommonResult<UserEditDto>> getUser(@RequestParam int index) {
        UserEditDto user = userServices.getUserByIndex(index);
        return ResponseEntity.ok(CommonResult.success(user));
    }

    @PatchMapping(value = "/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResult<EditResult>> patchUser(@ModelAttribute UserEditDto user, @RequestParam(value = "profile", required = false) MultipartFile profile, @AuthenticationPrincipal UserEntity loginUser) throws IOException {
        EditResult result = userServices.updateUser(user, profile);
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @PatchMapping(value = "/user-info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResult<EditResult>> patchUserInfo(@AuthenticationPrincipal UserEntity loginUser, @ModelAttribute UserEditDto user, @RequestParam(value = "profile", required = false) MultipartFile profile) throws IOException {
        EditResult result = this.userServices.updateUserInfo(loginUser.getIndex(), user, profile);
        return ResponseEntity.ok(CommonResult.success(result));
    }

    //user의 이미지를 물러오기 위한 api.
    @GetMapping(value = "/profile-image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getProfileImage(@AuthenticationPrincipal UserEntity loginUser) throws IOException {
        UserEntity user = userServices.getUserByEmpId(loginUser.getEmpId());
        byte[] profileImage = user.getProfile();
        String contentType = user.getProfileContentType();

        if (profileImage == null || profileImage.length == 0) {
            ClassPathResource res = new ClassPathResource("/static/default-profile.png");
            byte[] defaultBytes = res.getInputStream().readAllBytes();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .cacheControl(CacheControl.noCache())
                    .body(defaultBytes);

        }
        MediaType mediaType = (contentType == null || contentType.isBlank()) ? MediaType.IMAGE_PNG : MediaType.parseMediaType(contentType);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .cacheControl(CacheControl.noCache())
                .body(profileImage);

    }

    @GetMapping("/edit")
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


    @GetMapping("/permission")
    public String getUserPermission(Authentication authentication) {

        // 로그인 안 한 경우
        if (authentication == null || !authentication.isAuthenticated()) {
            return "NOT_LOGIN";
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

        if (isAdmin) {
            return "ADMIN";
        }

        if (isUser) {
            return "USER";
        }

        return "UNKNOWN";
    }
}