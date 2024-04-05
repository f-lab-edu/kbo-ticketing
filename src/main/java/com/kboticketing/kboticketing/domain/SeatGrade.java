package com.kboticketing.kboticketing.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class SeatGrade {

    private final Integer seatGradeId;
    private final String seatGradeName;
    private final String seatCount;
}
