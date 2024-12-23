package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.sender-email}")
    private String senderEmail;

    @Value("${spring.mail.sender-name}")
    private String senderName;

    @Override
    public void sendGreetingMessage(String to) {
        sendSimpleMessage(to, "Welcome to fast food restaurant!", "You have successfully registered!");
    }

    @Override
    public void sendOrderCompleteMessage(String to, String orderId) {
        sendSimpleMessage(to, "Your order has been completed!", "Order №" + orderId + " is completed.");
    }

    @Override
    public void sendOrderStatusChangedMessage(String to, String orderId, String status) {
        sendSimpleMessage(to, "Status of your order has been changed.", "Order №" + orderId + " is " + status + ".");
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom(senderName + " <" + senderEmail + ">");
            mailSender.send(message);
        } catch (Exception e) {
            log.warn("Mail service error:", e);
        }
    }
}
