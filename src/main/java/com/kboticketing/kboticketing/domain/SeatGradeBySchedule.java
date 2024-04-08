package com.kboticketing.kboticketing.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */
@Getter
@RequiredArgsConstructor
public class SeatGradeBySchedule {

    private final Integer scheduleId;
    private final Integer seatGradeId;
    private final String seatGradeName;
    private final String price;
    private final String seatRemain;
}
