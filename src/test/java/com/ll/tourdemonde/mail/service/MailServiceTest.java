package com.ll.tourdemonde.mail.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    @DisplayName("1. 메일 전송 테스트")
    void sendMailTest() {
        String email = "tourdemonde2024@gmail.com";
        String subject = "메일 전송 테스트";
        String body = "테스트용 메일입니다.";
        mailService.sendMail(email, subject, body);
    }

    @Test
    @DisplayName("2. 아이디 찾기 메일 전송 테스트")
    void sendUsernameMailTest() {
        String email = "tourdemonde2024@gmail.com";
        String username = "Username";
        mailService.sendUsernameMail(email, username);
    }

    @Test
    @DisplayName("3. 비밀번호 재발급 메일 전송 테스트")
    void sendNewPasswordTest() {
        String email = "tourdemonde2024@gmail.com";;
        mailService.sendNewPassword(email);
    }
}