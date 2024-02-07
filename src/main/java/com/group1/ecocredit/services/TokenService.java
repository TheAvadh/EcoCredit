package com.group1.ecocredit.services;

import com.group1.ecocredit.models.PasswordResetToken;

public interface TokenService {
    String generatePasswordResetToken(Integer userId);

    void savePasswordResetRequest(String token, Integer userId);

    boolean isValidToken(PasswordResetToken token);

    void inValidateToken(String token);

}
