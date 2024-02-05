package com.group1.ecocredit.controllers;

import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;

import com.group1.ecocredit.repositories.TokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group1.ecocredit.services.PasswordResetURIService;

import java.util.Optional;

@RestController
public class PasswordResetController {

    @Autowired
    private PasswordResetURIService passwordResetURIService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/resetpassword/{token}")
    public String resetPasswordGet(String token) {

        // TODO: Get data from token repository
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(token);

        // Returning null for now, ideally should be a re-direct
        if(passwordResetToken == null) return null;

        // TODO: Check token expiry

        if(passwordResetURIService.isValidToken(passwordResetToken)) {
            // decipher the token and verify if the user is enabled
            // return the reset password page
        }

        // TODO: return reset password page

        return "";
    }

    @PostMapping("/resetpassword/{token}")
    public User resetPasswordPost(@ModelAttribute User userModel) {

        // TODO: get user by email

        Optional<Long> userID = userModel.getId().describeConstable();


        if(userID.isPresent()) {
            Optional<User> userSearch = userRepository.findById(userID.get());

            if(userSearch.isPresent()) {

                User user = userSearch.get();

                user.setPasswordHash(userModel.getPasswordHash());

                // save acts as update
                userRepository.save(user);

                return user;
            }
        }

        return null;
    }

    @GetMapping("/resetpassword/request")
    public String resetPasssword(@RequestParam String email) {

        // TODO: get user by email
        // TODO: Generate URI

        User user = new User();
        String passwordResetURI = passwordResetURIService.getPasswordResetURI(user);

        // Call send email and pass the URI

        return passwordResetURI;

    }
}
