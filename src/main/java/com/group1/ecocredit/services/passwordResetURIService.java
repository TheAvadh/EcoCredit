package com.group1.ecocredit.services;

import com.group1.ecocredit.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Random;

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
        
        String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        
        Random random = new Random();

        String BASE_URI = env.getProperty("base.uri");
        
        int uriLength = Integer.parseInt(env.getProperty("password.reset.uri.length"));
        
        for(int i = 0; i < uriLength; i++) {
            salt.append(random.nextInt(CHARS.length()));
        }
        
        System.out.println(BASE_URI + salt.toString());
        return BASE_URI + salt.toString();

    }


}
