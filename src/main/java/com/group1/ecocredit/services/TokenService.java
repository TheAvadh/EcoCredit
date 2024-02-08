package com.group1.ecocredit.services;

import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;

public interface TokenService {
    String generatePasswordResetToken(Integer userId);

    void savePasswordResetToken(String token, User user);

    boolean isValidToken(PasswordResetToken token);

    void inValidateToken(String token);

}
