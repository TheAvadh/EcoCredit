package com.group1.ecocredit.services.implementations;

import com.google.common.hash.Hashing;
import com.group1.ecocredit.models.PasswordReset;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.PasswordResetRepository;
import com.group1.ecocredit.repositories.TokenRepository;
import com.group1.ecocredit.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${password.reset.validity.hours}")
    private Integer validityInHours;

    @Autowired
    TokenRepository tokenRepository;

    PasswordResetRepository passwordResetRepository;

    public TokenServiceImpl(PasswordResetRepository passwordResetRepository) {
        this.passwordResetRepository = passwordResetRepository;
    }

    @Override
    public String generatePasswordResetToken(Integer userId) {
        return java.util.UUID.randomUUID().toString();
    }


    @Override
    public boolean isValidToken(PasswordResetToken token) {
        return token.getExpirationTime().isAfter(LocalDateTime.now());
    }

    @Override
    public void inValidateToken(String token) {

        PasswordResetToken tokenToInvalidate = tokenRepository.findByToken(token);

        tokenToInvalidate.setUsed(true);

        tokenRepository.save(tokenToInvalidate);
    }

    @Override
    public void savePasswordResetRequest(String token, Integer userId) {

        var hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();

        var passwordResetRequest = PasswordReset
                .builder()
                .requestId(hashedToken)
                .userId(userId)
                .expiry(LocalDateTime.now().plusHours(validityInHours))
                .build();

        passwordResetRepository.save(passwordResetRequest);
    }
}
