package com.group1.ecocredit.services.implementations;

import com.google.common.hash.Hashing;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.PasswordResetTokenRepository;
import com.group1.ecocredit.services.TokenService;
import com.group1.ecocredit.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${password.reset.validity.hours}")
    private Integer validityInHours;

    PasswordResetTokenRepository passwordResetTokenRepository;

    public TokenServiceImpl(
            PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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

        String hashedToken = Utils.hash(token);
        PasswordResetToken tokenToInvalidate = passwordResetTokenRepository.findByToken(hashedToken);

        tokenToInvalidate.setUsed(true);

        passwordResetTokenRepository.save(tokenToInvalidate);
    }

    @Override
    public void savePasswordResetToken(String token, User user) {

        var hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();

        var passwordResetToken = PasswordResetToken
                .builder()
                .token(hashedToken)
                .user(user)
                .used(false)
                .expirationTime(LocalDateTime.now().plusHours(validityInHours))
                .build();

        passwordResetTokenRepository.save(passwordResetToken);
    }
}
