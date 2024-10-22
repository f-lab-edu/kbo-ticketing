package com.kboticketing.kboticketing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hazel
 */
@Getter
@NoArgsConstructor
@Entity(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;
    private Integer homeTeamId;
    private String name;
    private Integer awayTeamId;
    private String date;
    private Integer reservationLimit;

    @ManyToOne //양방향 관계에서 연관관계의 주인은 fk가 있는 곳
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;
}
