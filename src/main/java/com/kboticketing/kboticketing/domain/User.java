package com.kboticketing.kboticketing.domain;

import com.kboticketing.kboticketing.enums.Role;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author hazel
 */
@Getter
@ToString
public class User {

    private int userId;
    private final String name;
    private final String email;
    private final String password;
    private final Role role;
    private final LocalDateTime createdAt;

    public User(String name, String email, String password, Role role, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public User(int userId, String name, String email, String password, Role role,
        LocalDateTime createdAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }
}
