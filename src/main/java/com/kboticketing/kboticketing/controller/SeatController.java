package com.kboticketing.kboticketing.controller;

import com.kboticketing.kboticketing.domain.SeatGrade;
import com.kboticketing.kboticketing.dto.ReservationSeatDto;
import com.kboticketing.kboticketing.dto.SeatDto;
import com.kboticketing.kboticketing.service.SeatService;
import com.kboticketing.kboticketing.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/seat-grades/{id}")
    public ResponseEntity<CommonResponse> getSeatGrade(@PathVariable String id) {

        SeatGrade seatGrade = seatService.getSeatGrade(id);
        return ResponseEntity.ok(CommonResponse.ok(seatGrade));
    }

    @PostMapping("/seats")
    public void selectSeats(@RequestBody SeatDto seatDto) {

        //todo 로그인 작업 후 추가
        Integer userId = 1;
        seatService.selectSeats(seatDto, userId);
    }
}
