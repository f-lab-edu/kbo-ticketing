package com.kboticketing.kboticketing.controller;

import com.kboticketing.kboticketing.dto.EmailRequestDto;
import com.kboticketing.kboticketing.dto.SignInDto;
import com.kboticketing.kboticketing.dto.UserDto;
import com.kboticketing.kboticketing.dto.VerificationCodeDto;
import com.kboticketing.kboticketing.common.response.CommonResponse;
import com.kboticketing.kboticketing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("verification-code")
    public void sendVerificationCode(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        userService.sendVerificationCode(emailRequestDto);
    }

    @PostMapping("verification-code-validation")
    public void checkVerificationCode(@RequestBody @Valid VerificationCodeDto verificationCodeDto) {
        userService.checkVerificationCode(verificationCodeDto);
    }

    @PostMapping("sign-in")
    public ResponseEntity<CommonResponse> signIn(@RequestBody @Valid SignInDto signInDto) {
        return ResponseEntity.ok(CommonResponse.ok(userService.signIn(signInDto)));
    }
}

