package com.ll.tourdemonde.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    //메일 발송
    public void sendMail(String email, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            message.setFrom("tourdemonde2024@gmail.com");
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(subject);
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

    //아이디 찾기 메일 발송
    public void sendUsernameMail(String email, String username) {
        String subject = "아이디 찾기";
        String body = "<h3>아이디 찾기 결과는 다음과 같습니다.</h3>"
                + "<h1>" + username + "<h1>";

        sendMail(email, subject, body);
    }

    //비밀번호 재발급 메일 발송
    public String sendNewPassword(String email) {
        String newPassword;

        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String characters = uppercase + lowercase + numbers;
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        newPassword = password.toString();

        String subject = "비밀번호 재발급";
        String body = "<h3>임시 비밀번호는 다음과 같습니다.</h3>"
                + "<h1>" + newPassword + "<h1>";

        sendMail(email, subject, body);

        return newPassword;
    }
}