package com.kboticketing.kboticketing.controller;

import com.kboticketing.kboticketing.domain.ScheduleTeam;
import com.kboticketing.kboticketing.dto.ScheduleQueryParamDto;
import com.kboticketing.kboticketing.service.ScheduleService;
import com.kboticketing.kboticketing.utils.response.CommonResponse;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hazel
 */
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("schedules")
    public ResponseEntity<CommonResponse> getSchedules(
        ScheduleQueryParamDto scheduleQueryParamDto) {
        ArrayList<ScheduleTeam> schedules = scheduleService.getSchedules(scheduleQueryParamDto);
        return ResponseEntity.ok(CommonResponse.ok(schedules));
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<CommonResponse> getSchedule(@PathVariable String id) {
        return ResponseEntity.ok(CommonResponse.ok(scheduleService.getSchedule(id)));
    }

    @GetMapping("/schedules/{id}/seat-grade")
    public ResponseEntity<CommonResponse> getSeatGradeBySchedule(@PathVariable String id) {
        return ResponseEntity.ok(CommonResponse.ok(scheduleService.getSeatGradeBySchedule(id)));
    }
}
