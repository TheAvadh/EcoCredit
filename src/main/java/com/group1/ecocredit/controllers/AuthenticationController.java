package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.*;
import com.group1.ecocredit.models.ConfirmationToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.AuthenticationService;
import com.group1.ecocredit.services.PasswordService;
import com.group1.ecocredit.services.ConfirmationTokenService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")

public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final PasswordService passwordService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) throws MessagingException {
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signIn(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @GetMapping(path = "/verify-account")
    public ResponseEntity<Boolean> confirm(@RequestParam("token") String token){
        return ResponseEntity.ok(confirmationTokenService.confirmToken(token));

    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(
            @RequestBody ForgetPasswordRequest request) {
        try {
            var success = passwordService.forgetPassword(request);
            if (!success) {
                return ResponseEntity.status((HttpStatus.UNAUTHORIZED)).build();
            }
            return ResponseEntity.ok().build();
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
