package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.schedule.ScheduleRegisterDto;
import com.dev.workhiveback.entities.UserEntity;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.calendars.CalendarResult;
import com.dev.workhiveback.services.ScheduleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @RequestMapping(value = "/calendarData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommonResult<CalendarResult>> getCalendarData(
            @RequestParam(defaultValue = "personal") String filter,
            @AuthenticationPrincipal UserEntity loginUser) {
        CalendarResult result = scheduleService.getCalendarData(filter, loginUser.getEmpId(), loginUser.getTeamCode());
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommonResult<String>> postRegister(
            @RequestBody ScheduleRegisterDto register) {
        scheduleService.register(register);
        return ResponseEntity.ok(CommonResult.success("일정 등록 완료"));
    }
}
