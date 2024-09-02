package com.kboticketing.kboticketing.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author hazel
 */
@Getter
public class ScheduleInfo {

    private final Integer scheduleId;
    private final String scheduleName;
    private final String date;
    private final String stadiumName;

    @JsonCreator
    public ScheduleInfo(@JsonProperty("scheduleId") Integer scheduleId,
        @JsonProperty("scheduleName") String scheduleName,
        @JsonProperty("date") String date,
        @JsonProperty("stadiumName") String stadiumName) {
        this.scheduleId = scheduleId;
        this.scheduleName = scheduleName;
        this.date = date;
        this.stadiumName = stadiumName;
    }
}
