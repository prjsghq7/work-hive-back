package com.dev.workhiveback.controllers;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import com.dev.workhiveback.services.LeaveService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/leave")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @RequestMapping(value = "/calendarData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CalendarDto[] getAlarmList(/*@SessionAttribute(value = "signedUser", required = false) UserEntity signedUser*/) {
        return this.leaveService.getCalendarData(/*signedUser*/);
    }
}
