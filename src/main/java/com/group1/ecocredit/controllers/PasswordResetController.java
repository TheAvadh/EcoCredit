package com.group1.ecocredit.controllers;

import com.group1.ecocredit.models.PasswordResetNewPassword;
import com.group1.ecocredit.models.PasswordResetRequest;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.EcoCreditUser;
import com.group1.ecocredit.repositories.TokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.PasswordResetURIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.group1.ecocredit.utils.Utils;

@RestController
public class PasswordResetController {

    @Autowired
    private PasswordResetURIService passwordResetURIService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("api/reset-password/{token}")
    public String resetPasswordGet(String token) {

        String hashedToken = Utils.hash(token);

        // TODO: Get data from token repository
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(hashedToken);

        if (passwordResetToken != null && passwordResetURIService.isValidToken(passwordResetToken)) {

            // return the page for password reset page
        }
        // Returning null for now, ideally should be a re-direct
        return null;
    }

    @PostMapping("api/reset-password/{token}")
    public EcoCreditUser resetPasswordPost(String token, @ModelAttribute PasswordResetNewPassword passwordResetNewPasswordModel) {

        // TODO: get user by email


        String email = passwordResetNewPasswordModel.getEmail();

        EcoCreditUser user = userRepository.findByEmail(email);

        if (user != null) {
            user.setPasswordHash(Utils.hash(passwordResetNewPasswordModel.getNewPassword()));

            userRepository.save(user);

            passwordResetURIService.inValidateToken(token);

        }

        return null;
    }
}
