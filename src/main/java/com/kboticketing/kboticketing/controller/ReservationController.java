package com.kboticketing.kboticketing.controller;

import com.kboticketing.kboticketing.dto.ReservationDto;
import com.kboticketing.kboticketing.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hazel
 */
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final HttpServletRequest request;

    @PostMapping("/reservations")
    public void reserveSeats(@RequestBody ReservationDto reservationDto) {
        Integer userId = Integer.valueOf(request.getAttribute("userId")
                                                .toString());
        reservationService.reserveSeats(reservationDto, userId);
    }

    //나의 예매 목록 API

    //예매 상세 API

    //예매취소 API
}
