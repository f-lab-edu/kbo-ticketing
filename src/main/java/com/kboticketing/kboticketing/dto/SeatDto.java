package com.kboticketing.kboticketing.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */
@Getter
@RequiredArgsConstructor
public class SeatDto {

    private final Integer scheduleId;
    private final Integer seatGradeId;
    private final Integer seatNumber;
}