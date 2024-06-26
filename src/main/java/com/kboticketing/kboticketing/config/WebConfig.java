package com.kboticketing.kboticketing.config;

import com.kboticketing.kboticketing.config.interceptor.LoginInterceptor;
import com.kboticketing.kboticketing.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hazel
 */
@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class WebConfig implements WebMvcConfigurer {

    private final JwtService jwtService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtService))
                .order(1)
                .addPathPatterns("/", "/seats", "/reservations");
    }
}