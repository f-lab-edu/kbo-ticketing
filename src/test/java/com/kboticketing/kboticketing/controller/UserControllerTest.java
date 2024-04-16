package com.kboticketing.kboticketing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kboticketing.kboticketing.dto.EmailRequestDto;
import com.kboticketing.kboticketing.dto.UserDto;
import com.kboticketing.kboticketing.dto.VerificationCodeDto;
import com.kboticketing.kboticketing.service.UserService;
import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author hazel
 */
@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;

    @Test
    @DisplayName("[FAIL] 회원가입 비밀번호 일치 테스트")
    void signUpPasswordMatchTest() throws Exception {

        //given
        UserDto userDto = new UserDto("홍길동", "aaaa@naver.com", "123123", "Pa$$w0rd!", "123123");
        String json = new ObjectMapper().writeValueAsString(userDto);

        willThrow(new CustomException(ErrorCode.PASSWORD_MISMATCH))
            .given(userService)
            .signUp(any());

        //when, then
        mockMvc.perform(post("/users")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[FAIL] 회원가입 비밀번호 유효성 검사 테스트")
    void signUpDTOValidTest() throws Exception {

        //given
        UserDto userDto = new UserDto("원빈", "aaaa@naver.com", "123123", "1234", "1234");
        String json = new ObjectMapper().writeValueAsString(userDto);

        //when, then
        mockMvc.perform(post("/users")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[SUCCESS] 회원가입 성공 테스트")
    void signUpSuccessTest() throws Exception {

        //given
        UserDto userDto = new UserDto("공유", "abcde@naver.com", "123123", "Pa$$w0rd!", "Pa$$w0rd!");
        String json = new ObjectMapper().writeValueAsString(userDto);

        //when,then
        mockMvc.perform(post("/users")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[SUCCESS] 이메일 인증 성공 테스트")
    void sendVerificationTest() throws Exception {

        //given
        EmailRequestDto emailRequestDto = new EmailRequestDto("waithjno@gmail.com");
        String json = new ObjectMapper().writeValueAsString(emailRequestDto);

        //when,then
        mockMvc.perform(post("/verification-code")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[FAIL] 이메일 입력 테스트")
    void sendVerificationEmailInputTest() throws Exception {
        //given
        EmailRequestDto emailRequestDto = new EmailRequestDto("");
        String json = new ObjectMapper().writeValueAsString(emailRequestDto);

        //when, then
        mockMvc.perform(post("/verification-code")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[FAIL] 이메일 유효성 검증 테스트")
    void sendVerificationEmailValidTest() throws Exception {

        //given
        EmailRequestDto emailRequestDto = new EmailRequestDto("aaa");
        String json = new ObjectMapper().writeValueAsString(emailRequestDto);

        //when, then
        mockMvc.perform(post("/verification-code")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[SUCCESS] 인증번호 확인 테스트")
    void checkVerificationCodeTest() throws Exception {

        //given
        VerificationCodeDto verificationCodeDto = new VerificationCodeDto("aaaa@naver.com",
            "123123");
        String json = new ObjectMapper().writeValueAsString(verificationCodeDto);

        //when, then
        mockMvc.perform(post("/verification-code-validation")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }


    @Test
    @DisplayName("[FAIL] 인증번호 미입력 테스트")
    public void checkVerificationCodeValidationTest() throws Exception {

        //given
        VerificationCodeDto verificationCodeDto = new VerificationCodeDto("aaaa@naver.com",
            "");
        String json = new ObjectMapper().writeValueAsString(verificationCodeDto);

        //when, then
        mockMvc.perform(post("/verification-code-validation")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
}