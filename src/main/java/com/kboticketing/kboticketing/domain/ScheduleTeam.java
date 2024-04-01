package com.kboticketing.kboticketing.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */
@RequiredArgsConstructor
@Getter
public class ScheduleTeam {

    private final Integer scheduleId;
    private final String scheduleName;
    private final String stadiumName;
    private final String date;
}
