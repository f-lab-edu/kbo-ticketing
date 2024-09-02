package com.kboticketing.kboticketing.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author hazel
 */
@Getter
@EqualsAndHashCode
public class SeatGrade {

    private final Integer seatGradeId;
    private final String seatGradeName;
    private final String seatCount;

    @JsonCreator
    public SeatGrade(@JsonProperty("seatGradeId") Integer seatGradeId,
        @JsonProperty("seatGradeName") String seatGradeName,
        @JsonProperty("seatCount") String seatCount) {
        this.seatGradeId = seatGradeId;
        this.seatGradeName = seatGradeName;
        this.seatCount = seatCount;
    }
}
