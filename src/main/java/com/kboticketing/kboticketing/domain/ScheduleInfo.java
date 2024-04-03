package com.kboticketing.kboticketing.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */
@Getter
@RequiredArgsConstructor
public class ScheduleInfo {

    private final Integer scheduleId;
    private final String scheduleName;
    private final String date;
    private final String stadiumName;
}
