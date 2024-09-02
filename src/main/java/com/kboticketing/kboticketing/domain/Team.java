package com.kboticketing.kboticketing.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author hazel
 */
@Getter
public class Team {

    private final Integer teamId;
    private final String name;

    @JsonCreator
    public Team(@JsonProperty("teamId") Integer teamId, @JsonProperty("name") String name) {
        this.teamId = teamId;
        this.name = name;
    }
}
