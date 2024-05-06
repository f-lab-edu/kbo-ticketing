package com.kboticketing.kboticketing.interceptor;

import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import com.kboticketing.kboticketing.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author hazel
 */

@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

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
        if (!token.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String userId = jwtService.validateJwt(splitToken[1]);
        request.setAttribute("userId", userId);
        return true;
    }
}
