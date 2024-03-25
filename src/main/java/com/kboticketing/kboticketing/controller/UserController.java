package com.kboticketing.kboticketing.controller;

import com.kboticketing.kboticketing.dto.UserDto;
import com.kboticketing.kboticketing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hazel
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("users")
    public void signUp(@RequestBody @Valid UserDto userDto) {
        userService.signUp(userDto);
    }
}

