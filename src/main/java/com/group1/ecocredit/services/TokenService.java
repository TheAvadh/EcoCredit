package com.group1.ecocredit.services;

public interface TokenService {
    String generatePasswordResetToken(Integer userId);

    void savePasswordResetRequest(String token, Integer userId);
}
