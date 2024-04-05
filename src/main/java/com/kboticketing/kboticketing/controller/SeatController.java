package com.kboticketing.kboticketing.controller;

import com.kboticketing.kboticketing.dto.ReservationSeatDto;
import com.kboticketing.kboticketing.service.SeatService;
import com.kboticketing.kboticketing.utils.response.CommonResponse;
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
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/schedules/{scheduleId}/seat-grades/{seatGradeId}")
    public ResponseEntity<CommonResponse> getSeatsByGrade(@PathVariable String scheduleId,
        @PathVariable String seatGradeId) {

        ReservationSeatDto seatsBySeatGrade = seatService.getSeatsByGrade(scheduleId, seatGradeId);
        return ResponseEntity.ok(CommonResponse.ok(seatsBySeatGrade));
    }
}
