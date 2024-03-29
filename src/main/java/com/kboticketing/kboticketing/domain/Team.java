package com.kboticketing.kboticketing.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */
@RequiredArgsConstructor
@Getter
public class Team {

    private final Integer teamId;
    private final String name;
}
