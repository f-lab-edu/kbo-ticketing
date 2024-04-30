package com.kboticketing.kboticketing.Interceptor;

import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * todo 수정 예정
 *
 * @author hazel
 */

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        String requestURI = request.getRequestURI();
        log.info("[interceptor] requestURI : " + requestURI);

        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        String[] splitToken = token.split(" ");
        String userId = splitToken[1];
        request.setAttribute("userId", userId);
        return true;
    }
}
