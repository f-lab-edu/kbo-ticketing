package com.kboticketing.kboticketing.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hazel
 */
@Getter
@Entity(name = "teams")
@NoArgsConstructor
public class Team {

    @Id
    private Integer teamId;
    private String name;

    @JsonCreator
    public Team(@JsonProperty("teamId") Integer teamId, @JsonProperty("name") String name) {
        this.teamId = teamId;
        this.name = name;
    }
}
