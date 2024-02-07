package com.group1.ecocredit.services;

import com.group1.ecocredit.models.User;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendProfileUpdateNotification(User user) throws MessagingException;
}
