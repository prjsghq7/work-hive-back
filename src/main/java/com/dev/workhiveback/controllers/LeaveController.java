package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.leave.LeaveListDto;
import com.dev.workhiveback.entities.LeaveEntity;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.calendars.CalendarResult;
import com.dev.workhiveback.results.reasons.leave.LeaveResult;
import com.dev.workhiveback.services.LeaveService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/leave")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @RequestMapping(value = "/calendarData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommonResult<CalendarResult>> getAlarmList(
            @RequestParam(defaultValue = "personal") String filter,
            @AuthenticationPrincipal UserEntity loginUser) {
        CalendarResult result = leaveService.getCalendarData(filter, loginUser.getEmpId(), loginUser.getTeamCode());
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult<LeaveResult>> getLeaveList(
            @RequestParam(defaultValue = "all") String tab,
            @AuthenticationPrincipal UserEntity loginUser) {
        List<LeaveListDto> leaves = leaveService.findByTab(tab, loginUser.getEmpId(), loginUser.getTeamCode());

        LeaveResult result = LeaveResult.builder()
                .leaves(leaves)
                .build();

        return ResponseEntity.ok(CommonResult.success(result));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult<LeaveResult>> getLeave() {
        List<LeaveListDto> leaves = leaveService.findAll();

        LeaveResult result = LeaveResult.builder()
                .leaves(leaves)
                .build();

        return ResponseEntity.ok(CommonResult.success(result));
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommonResult<String>> postRequest(@AuthenticationPrincipal UserEntity loginUser, @RequestBody LeaveEntity leave) {
        leaveService.request(loginUser.getEmpId(), leave);
        return ResponseEntity.ok(CommonResult.success("연차 요청 완료"));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult<LeaveListDto>> getLeaveDetail(@RequestParam int index) {
        LeaveListDto leave = leaveService.findByIndex(index);
        return ResponseEntity.ok(CommonResult.success(leave));
    }

    @RequestMapping(value = "/action", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommonResult<String>> patchLeaveAction(
            @RequestParam int index,
            @RequestParam String action,
            @AuthenticationPrincipal UserEntity loginUser) {
        String message = leaveService.patchAction(index, action, loginUser.getEmpId());
        return ResponseEntity.ok(CommonResult.success(message));
    }
}
