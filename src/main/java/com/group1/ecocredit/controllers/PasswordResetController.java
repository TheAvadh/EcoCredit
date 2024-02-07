package com.group1.ecocredit.controllers;

import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.PasswordRepository;
import com.group1.ecocredit.repositories.TokenRepository;
import com.group1.ecocredit.repositories.EcoCreditUserRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.PasswordResetURIService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> resetPasswordGet(@PathVariable(required = true) String token) {

        String hashedToken = Utils.hash(token);

        // TODO: Get data from token repository
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(hashedToken);

        if (passwordResetToken != null && passwordResetURIService.isValidToken(passwordResetToken)) {

            // return the page for password reset page

            return ResponseEntity.ok("password return page");
        }
        // Returning null for now, ideally should be a re-direct
        return ResponseEntity.badRequest().build();
    }

    @Transactional
    @PostMapping("api/reset-password/{token}")
    public ResponseEntity<User> resetPasswordPost(
            @PathVariable(required = true) String token,
            @RequestBody(required = true) PasswordResetNewPassword passwordResetNewPasswordModel) {

        if (!Objects.equals(passwordResetNewPasswordModel.getNewPassword(), passwordResetNewPasswordModel.getNewPasswordRepeat()))
            return ResponseEntity.badRequest().build();

        if(tokenRepository.findByToken(Utils.hash(token)) == null) {
            return ResponseEntity.badRequest().build();
        }

        String email = passwordResetNewPasswordModel.getEmail();

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userOptional.get();

        if (!tokenRepository.findByToken(Utils.hash(token)).isUsed()) {

            user.setPassword(Utils.hash(passwordResetNewPasswordModel.getNewPassword()));

            userRepository.save(user);

            passwordResetURIService.inValidateToken(Utils.hash(token));

            return ResponseEntity.ok(user);
        }

        return ResponseEntity.badRequest().build();
    }
}
