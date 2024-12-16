package com.fastfoodrestaraunt.backend.service;

public interface MailService {
    void sendGreetingMessage(String to);

    void sendOrderCompleteMessage(String to, String orderId);

    void sendOrderStatusChangedMessage(String to, String orderId, String status);
}
