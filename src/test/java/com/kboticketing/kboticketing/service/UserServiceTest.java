package com.kboticketing.kboticketing.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import com.kboticketing.kboticketing.dao.UserMapper;
import com.kboticketing.kboticketing.domain.User;
import com.kboticketing.kboticketing.dto.EmailRequestDto;
import com.kboticketing.kboticketing.dto.UserDto;
import com.kboticketing.kboticketing.utils.EmailUtils;
import com.kboticketing.kboticketing.utils.NumberUtils;
import com.kboticketing.kboticketing.utils.enums.Role;
import com.kboticketing.kboticketing.utils.exception.CustomException;
import com.kboticketing.kboticketing.utils.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author hazel
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserMapper userMapper;
    @Mock
    RedisTemplate<String, String> redisTemplate;
    @Mock
    ValueOperations<String, String> valueOperations;
    @Mock
    EmailUtils emailUtils;

    @Test
    @DisplayName("[SUCCESS] 회원가입 성공 테스트")
    public void signUpSuccessTest() {

        // given
        UserDto userDto = new UserDto("홍길동", "aaa@naver.com", "123123", "123123", "123123");
        given(userMapper.selectByEmail(any())).willReturn(null);

        //when, then : 아무런 예외를 던지지 않음.
        assertDoesNotThrow(() -> userService.signUp(userDto));
    }

    @Test
    @DisplayName("[FAIL] 회원가입 비밀번호 일치 테스트")
    public void signUpPasswordMismatchTest() {

        //given
        UserDto userDto = new UserDto("홍길동", "aaa@naver.com", "123123", "123123", "000000");

        // when
        CustomException customException = assertThrows(CustomException.class, () -> {
            userService.signUp(userDto);
        });

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.PASSWORD_MISMATCH);
    }

    @Test
    @DisplayName("[FAIL] 회원가입 이메일 존재 여부 확인 테스트")
    public void signUpEmailCheckTest() {

        //given
        UserDto userDto = new UserDto("홍길동", "aaa@naver.com", "123123", "123123", "123123");
        User user = new User("홍길동", "aaa@naver.com", "123123", Role.USER, LocalDateTime.now());
        BDDMockito.given(userMapper.selectByEmail(userDto.getEmail()))
                  .willReturn(user);

        //when
        CustomException customException = assertThrows(CustomException.class, () -> {
            userService.signUp(userDto);
        });

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.EMAIL_ALREADY_EXISTS);
    }

    @Test
    @DisplayName("[SUCCESS] 이메일 인증 성공 테스트")
    public void sendVerificationSuccessTest() {

        //given
        EmailRequestDto emailRequestDto = new EmailRequestDto("hi@naver.com");
        given(redisTemplate.hasKey(anyString())).willReturn(false);

        mockStatic(NumberUtils.class);
        given(NumberUtils.createRandomNumber()).willReturn("123456");
        given(redisTemplate.opsForValue()).willReturn(valueOperations);

        //when
        userService.sendVerificationCode(emailRequestDto);

        //then
        verify(emailUtils).sendEmail(anyString(), anyString());
        verify(valueOperations).set(emailRequestDto.getEmail(), "123456", 5, TimeUnit.MINUTES);
    }

    @Test
    @DisplayName("[FAIL] 이메일 인증 잦은 요청 테스트")
    public void sendVerificationAgainFailTest() {

        //given
        EmailRequestDto emailRequestDto = new EmailRequestDto("111@naver.com");
        given(redisTemplate.hasKey(anyString())).willReturn(true);

        // when
        CustomException customException = assertThrows(CustomException.class, () -> {
            userService.sendVerificationCode(emailRequestDto);
        });

        //then
        assertThat(customException.getErrorCode()).isEqualTo(
            ErrorCode.TOO_FREQUENT_VERIFICATION_REQUEST);
    }

    @Test
    @DisplayName("[FAIL] 이메일 인증 테스트")
    public void sendVerificationFailTest() {

        //given
        EmailRequestDto emailRequestDto = new EmailRequestDto("111@naver.com");
        given(redisTemplate.hasKey(anyString())).willReturn(null);

        // when
        CustomException customException = assertThrows(CustomException.class, () -> {
            userService.sendVerificationCode(emailRequestDto);
        });

        //then
        assertThat(customException.getErrorCode()).isEqualTo(
            ErrorCode.FAIL_SEND_EMAIL);
    }
}




