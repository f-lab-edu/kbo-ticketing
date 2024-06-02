package com.kboticketing.kboticketing.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kboticketing.kboticketing.common.util.PasswordUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hazel
 */
public class PasswordUtilsTest {

    @Test
    @DisplayName("[SUCCESS] 비밀번호 암호화")
    public void encryptPasswordTest() {

        //given
        String password = "password123";

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        //when
        String encryptedPassword = PasswordUtils.encryptPassword(password);

        //then
        assertNotNull(encryptedPassword);
        assertTrue(bCryptPasswordEncoder.matches(password, encryptedPassword));
    }
}
