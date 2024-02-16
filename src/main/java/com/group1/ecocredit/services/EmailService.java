package com.group1.ecocredit.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerifyAccountEmail(String email, String token) throws MessagingException;
    void sendResetPasswordEmail(String email, String token) throws MessagingException;
}
