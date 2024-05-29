package com.kboticketing.kboticketing.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author hazel
 */
@Getter
public class ScheduleTeam {

    private final Integer scheduleId;
    private final String scheduleName;
    private final String stadiumName;
    private final String date;

    @JsonCreator
    public ScheduleTeam(@JsonProperty("scheduleId") Integer scheduleId,
        @JsonProperty("scheduleName") String scheduleName,
        @JsonProperty("stadiumName") String stadiumName, @JsonProperty("date") String date) {
        this.scheduleId = scheduleId;
        this.scheduleName = scheduleName;
        this.stadiumName = stadiumName;
        this.date = date;
    }
}
