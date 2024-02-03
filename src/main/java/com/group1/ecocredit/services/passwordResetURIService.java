package com.group1.ecocredit.services;

import com.group1.ecocredit.models.User;
import com.group1.ecocredit.models.passwordResetToken;

import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class passwordResetURIService {

    @Autowired Environment env;

    /*
     * TODO: Switch from randomizing the uri to jwt token
     * 
     * Need to get user object completed to generate the token for it
     * 
     * 
     */
    public String getPasswordResetURI(User user) {

        passwordResetToken reetToken = new passwordResetToken();

        reetToken.setUser(user);
        reetToken.setExpirationTime(LocalDateTime.now().plusYears(1000));
        reetToken.setToken(UUID.randomUUID().toString());

        String BASE_URI = env.getProperty("base.uri");

        // TODO: use passwordResetToken object to generate a jwt token

        // TODO: store url link to DB table - passwordResetTickets

        // TODO: invalidate token once used 

        return BASE_URI + reetToken.getToken();

    }


}
