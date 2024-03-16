package com.group1.ecocredit.services;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.User;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendProfileUpdateEmail(User user) throws MessagingException;
    void sendVerifyAccountEmail(String email, String token) throws MessagingException;
    void sendResetPasswordEmail(String email, String token) throws MessagingException;

    void sendPickupScheduledEmail(Pickup pickup) throws MessagingException;
}
