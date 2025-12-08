package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.LoginDto;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.CodeResult;
import com.dev.workhiveback.results.reasons.LoginResult;
import com.dev.workhiveback.results.reasons.RegisterResult;
import com.dev.workhiveback.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value= "/register")
    public ResponseEntity<CommonResult<RegisterResult>> register(
            @RequestParam String id,
            @RequestParam String password
    ) {
        RegisterResult result = userServices.register(id, password);
        return ResponseEntity.ok(CommonResult.success(result));
    }


    @GetMapping(value = "/team-list")
    public ResponseEntity<CommonResult<CodeResult>> getTeamList() {
        CodeResult result = userServices.getTeamCodes();
        System.out.println("team-list: " + result);
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @GetMapping(value = "/state-list")
    public ResponseEntity<CommonResult<CodeResult>> getUserStateList() {
        CodeResult result = userServices.getUserStateCodes();
        return ResponseEntity.ok(CommonResult.success(result));
    }
}