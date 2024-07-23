package com.example.otpauthentication.service;

import com.example.otpauthentication.dto.EmailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendEmail(EmailMessage emailMessage);
}
