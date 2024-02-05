package com.group1.ecocredit.services;

import com.group1.ecocredit.models.User;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
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
    public String getPasswordResetURI(User user) {


        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setUser(user);
        resetToken.setExpirationTime(LocalDateTime.now().plusMinutes(Objects.requireNonNull(env.getProperty("validity.minutes")).));
        resetToken.setToken(UUID.randomUUID().toString());

        String BASE_URI = env.getProperty("base.uri");

        // TODO: use passwordResetToken object to generate a jwt token

        // TODO: store url link to DB table - passwordResetTickets
        tokenRepository.save(resetToken);

        // TODO: invalidate token once used 

        return BASE_URI + resetToken.getToken();

    }

    public boolean isValidToken(PasswordResetToken token) {

        return token.getExpirationTime().isAfter(LocalDateTime.now());
    }

}
