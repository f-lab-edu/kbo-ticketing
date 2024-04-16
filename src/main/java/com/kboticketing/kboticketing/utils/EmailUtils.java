package com.kboticketing.kboticketing.utils;

import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

/**
 * @author hazel
 */
@Slf4j
@Component
public class EmailUtils {

    private final String user;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailUtils(@Value("${spring.mail.user}") String user, JavaMailSender javaMailSender,
        SpringTemplateEngine springTemplateEngine) {
        this.user = user;
        this.javaMailSender = javaMailSender;
        this.templateEngine = springTemplateEngine;
    }

    public void sendEmail(String verificationCode, String toEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false,
                "UTF-8");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject("KBO-TICKETING 회원가입 인증번호입니다.");
            mimeMessageHelper.setFrom(user);
            mimeMessageHelper.setText(setContext(verificationCode, "email"), true);

            //메일 전송
            javaMailSender.send(mimeMessage);
            log.info(
                "Successfully sent verification code. Recipient: {}, Sender: {}, code: {}",
                toEmail, user, verificationCode);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.FAIL_SEND_EMAIL);
        }
    }

    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }
}
