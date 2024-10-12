package com.spring.tour.service.impl;

import com.spring.tour.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {


    private final JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String subject, String text
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("my@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }
}