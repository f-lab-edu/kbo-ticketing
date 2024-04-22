package com.kboticketing.kboticketing.domain;

import com.kboticketing.kboticketing.enums.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author hazel
 */
@Getter
@ToString
@RequiredArgsConstructor
public class User {

    private long userId;
    private final String name;
    private final String email;
    private final String password;
    private final Role role;
    private final LocalDateTime createdAt;
}
