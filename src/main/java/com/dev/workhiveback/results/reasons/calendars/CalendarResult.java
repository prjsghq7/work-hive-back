package com.dev.workhiveback.results.reasons.calendars;

import com.dev.workhiveback.dtos.leave.CalendarDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CalendarResult {
    List<CalendarDto> calendarList;
}
