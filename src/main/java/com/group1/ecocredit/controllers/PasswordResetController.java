package com.group1.ecocredit.controllers;

import com.group1.ecocredit.models.PasswordResetRequest;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.EcoCreditUser;
import com.group1.ecocredit.repositories.TokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.PasswordResetURIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public EcoCreditUser resetPasswordPost(@ModelAttribute EcoCreditUser userModel) {

        // TODO: get user by email

        Optional<Long> userID = userModel.getId().describeConstable();


        if(userID.isPresent()) {
            Optional<EcoCreditUser> userSearch = userRepository.findById(userID.get());

            if(userSearch.isPresent()) {

                EcoCreditUser user = userSearch.get();

                // TODO: Set new password

                // save acts as update
                userRepository.save(user);

                return user;
            }
        }

        return null;
    }

    @GetMapping("/resetpassword/request")
    public String resetPasssword(@RequestBody PasswordResetRequest passwordResetInput) {

        // TODO: get user by email

        EcoCreditUser user = userRepository.findByEmail(passwordResetInput.getEmail());

        System.out.println(user.getFirstName());


        if(user != null) {
            // TODO: Generate URI
            PasswordResetToken resetToken = passwordResetURIService.getPasswordResetToken(user);
            // Call send email and pass the URI

            return resetToken.getToken();
        }


        return null;
    }
}
