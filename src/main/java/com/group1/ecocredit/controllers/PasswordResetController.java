package com.group1.ecocredit.controllers;

import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.PasswordRepository;
import com.group1.ecocredit.repositories.TokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.PasswordResetURIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
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

    @Autowired
    PasswordRepository passwordRepository;

    @GetMapping("api/reset-password/{token}")
    public String resetPasswordGet(@PathVariable(required = true) String token) {

        String hashedToken = Utils.hash(token);

        // TODO: Get data from token repository
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(hashedToken);

        if (passwordResetToken != null && passwordResetURIService.isValidToken(passwordResetToken)) {

            // return the page for password reset page

            return "password return page";
        }
        // Returning null for now, ideally should be a re-direct
        return null;
    }

    @PostMapping("api/reset-password/{token}")
    public EcoCreditUser resetPasswordPost(
            @PathVariable(required = true) String token,
            @RequestBody(required = true) PasswordResetNewPassword passwordResetNewPasswordModel) {

        if (!Objects.equals(passwordResetNewPasswordModel.getNewPassword(), passwordResetNewPasswordModel.getNewPasswordRepeat()))
            return null;

        String email = passwordResetNewPasswordModel.getEmail();

        EcoCreditUser user = userRepository.findByEmail(email);

        if (user != null) {

            Password password = passwordRepository.findByUser(user);

            password.setPassword(Utils.hash(passwordResetNewPasswordModel.getNewPassword()));

            passwordRepository.save(password);

            user.setPassword(password);
            userRepository.save(user);

            passwordResetURIService.inValidateToken(token);

            return user;
        }

        return null;
    }
}
