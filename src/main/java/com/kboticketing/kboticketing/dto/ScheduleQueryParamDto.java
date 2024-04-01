package com.kboticketing.kboticketing.dto;

import java.time.LocalDateTime;
import lombok.Getter;

/**
 * @author hazel
 */
@Getter
public class ScheduleQueryParamDto {

    private final Integer teamId;
    private final Integer month;
    private final Integer day;
    private final Integer stadiumId;

    public ScheduleQueryParamDto(Integer teamId, Integer month, Integer day, Integer stadiumId) {
        this.teamId = teamId;
        // month 필드가 null이라면 현재 월 설정
        this.month = month == null ? LocalDateTime.now()
                                                  .getMonthValue() : month;
        this.day = day;
        this.stadiumId = stadiumId;
    }
}
