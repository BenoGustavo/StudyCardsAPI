package com.gorges.studycardsapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.gorges.studycardsapi.models.UserEntity;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.app.host}")
    private String host;

    public void sendVerificationEmail(UserEntity user, String token) {

        String recipientAddress = user.getEmail();
        String subject = "Account Verification";
        String confirmationUrl = host + "/api/auth/verify-email?token=" + token;
        String message = "Clique no link para verificar sua conta:\n" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }
}