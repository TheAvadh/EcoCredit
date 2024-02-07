package com.group1.ecocredit.services.implementations;

import com.google.common.hash.Hashing;
import com.group1.ecocredit.models.PasswordResetRequest;
import com.group1.ecocredit.repositories.PasswordResetRepository;
import com.group1.ecocredit.services.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${password.reset.validity.hours}")
    private Integer validityInHours;

    PasswordResetRepository passwordResetRepository;

    public TokenServiceImpl(PasswordResetRepository passwordResetRepository) {
        this.passwordResetRepository = passwordResetRepository;
    }

    @Override
    public String generatePasswordResetToken(Integer userId) {
        return java.util.UUID.randomUUID().toString();
    }

    @Override
    public void savePasswordResetRequest(String token, Integer userId) {

        var hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();

        var passwordResetRequest = PasswordResetRequest
                .builder()
                .requestId(hashedToken)
                .userId(userId)
                .expiry(LocalDateTime.now().plusHours(validityInHours))
                .build();

        passwordResetRepository.save(passwordResetRequest);
    }
}
