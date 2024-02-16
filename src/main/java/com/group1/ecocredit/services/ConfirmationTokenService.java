package com.group1.ecocredit.services;

import com.group1.ecocredit.models.ConfirmationToken;
import com.group1.ecocredit.models.User;

public interface ConfirmationTokenService {

    String generateConfirmationToken(Integer userId);
    String saveConfirmationToken(String token, User user);
    boolean confirmToken(String token);
    boolean isValidToken(ConfirmationToken token);
}
