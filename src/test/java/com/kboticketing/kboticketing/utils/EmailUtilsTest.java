package com.kboticketing.kboticketing.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

import com.kboticketing.kboticketing.common.util.EmailUtils;
import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

/**
 * @author hazel
 */
@ExtendWith(MockitoExtension.class)
public class EmailUtilsTest {

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private SpringTemplateEngine springTemplateEngine;

    @Test
    @DisplayName("[SUCCESS] 이메일 전송 테스트")
    public void sendEmailTest() {

        // given
        String verificationCode = "123456";
        String toEmail = "toUser@example.com";

        given(javaMailSender.createMimeMessage()).willReturn(mimeMessage);
        given(springTemplateEngine.process(anyString(), any())).willReturn("Email Content");

        // when
        EmailUtils emailUtils = new EmailUtils("from@example.com", javaMailSender,
            springTemplateEngine);
        emailUtils.sendEmail(verificationCode, toEmail);

        //then
        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    @DisplayName("[FAIL] 이메일 전송 테스트")
    public void sendEmailFailTest() {

        // given
        String verificationCode = "123456";
        String toEmail = "toUser@example.com";
        given(javaMailSender.createMimeMessage()).willReturn(mimeMessage);

        // when
        EmailUtils emailUtils = new EmailUtils("from@example.com", javaMailSender,
            springTemplateEngine);
        CustomException customException = assertThrows(CustomException.class,
            () -> emailUtils.sendEmail(verificationCode, toEmail));

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.FAIL_SEND_EMAIL);
    }
}
