package com.group1.ecocredit.services;

import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.EcoCreditUser;
import com.group1.ecocredit.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class PasswordResetURIService {


    @Autowired
    Environment env;

    @Autowired
    TokenRepository tokenRepository;

    /*
     * TODO: Switch from randomizing the uri to jwt token
     *
     * Need to get user object completed to generate the token for it
     *
     *
     */
    public PasswordResetToken getPasswordResetToken(EcoCreditUser user) {

        Long validityHours = Long.parseLong(Objects.requireNonNull(env.getProperty("password.reset.validity.hours")));

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setExpirationTime(LocalDateTime.now().plusHours(validityHours));
        resetToken.setToken(UUID.randomUUID().toString());

        // TODO: use passwordResetToken object to generate a jwt token

        // TODO: store url link to DB table - passwordresettoken
        tokenRepository.save(resetToken);

        return resetToken;

    }

    public boolean isValidToken(PasswordResetToken token) {

        return token.getExpirationTime().isAfter(LocalDateTime.now());
    }


    public void inValidateToken(String token) {

        PasswordResetToken tokenToInvalidate = tokenRepository.findByToken(token);

        tokenToInvalidate.setUsed(true);

        tokenRepository.save(tokenToInvalidate);

    }

}
