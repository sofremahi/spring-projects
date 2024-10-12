package com.spring.tour.service;

public interface MailService {
    public void sendSimpleMessage(
            String to, String subject, String text
    );
}
