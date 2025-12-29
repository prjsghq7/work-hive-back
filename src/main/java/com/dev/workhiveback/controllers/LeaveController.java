package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.entities.LeaveEntity;
import com.dev.workhiveback.results.CommonResult;
import com.dev.workhiveback.results.reasons.calendars.CalendarResult;
import com.dev.workhiveback.results.reasons.leave.LeaveResult;
import com.dev.workhiveback.services.LeaveService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResult<CalendarResult>> getAlarmList(/*@SessionAttribute(value = "signedUser", required = false) UserEntity signedUser*/) {
        CalendarResult result = leaveService.getCalendarData(/*signedUser*/);
        return ResponseEntity.ok(CommonResult.success(result));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResult<LeaveResult>> getLeave() {
        List<LeaveEntity> leaves = leaveService.findAll();

        LeaveResult result = LeaveResult.builder()
                .leaves(leaves)
                .build();

        return ResponseEntity.ok(CommonResult.success(result));
    }
}
