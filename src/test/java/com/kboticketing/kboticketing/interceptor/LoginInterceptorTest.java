package com.kboticketing.kboticketing.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import com.kboticketing.kboticketing.service.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author hazel
 */
@ExtendWith(MockitoExtension.class)
public class LoginInterceptorTest {

    @InjectMocks
    private LoginInterceptor loginInterceptor;
    @Mock
    private JwtService jwtService;
    @Mock
    private MockHttpServletRequest request;

    @Mock
    private MockHttpServletResponse response;

    @Test
    @DisplayName("[SUCCESS]로그인 인터셉터 테스트")
    public void preHandleTest() {

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        //given
        request.addHeader("Authorization", "Bearer validToken");
        BDDMockito.given(jwtService.validateJwt("validToken"))
                  .willReturn("1");

        //when
        boolean result = loginInterceptor.preHandle(request, response, null);

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("[FAIL]로그인 인터셉터 토큰 확인 테스트")
    public void preHandleTokenCheckTest() {

        //given
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.addHeader("", "");

        //when
        CustomException customException = assertThrows(CustomException.class,
            () -> loginInterceptor.preHandle(request, response, null));

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.LOGIN_REQUIRED);
    }


    @Test
    @DisplayName("[FAIL]로그인 인터셉터 토큰 검증 테스트")
    public void preHandleJwtValidateTest() {

        //given
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.addHeader("Authorization", " ");

        //when
        CustomException customException = assertThrows(CustomException.class,
            () -> loginInterceptor.preHandle(request, response, null));

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.INVALID_TOKEN);
    }
}
