package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.*;
import com.group1.ecocredit.dto.PasswordResetRequest;
import com.group1.ecocredit.enums.HttpMessage;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.AuthenticationService;
import com.group1.ecocredit.services.PasswordService;
import com.group1.ecocredit.services.ConfirmationTokenService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")

public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final PasswordService passwordService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            return ResponseEntity.ok(authenticationService.signup(signUpRequest));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signinRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signIn(signinRequest));
        }
        catch(Exception e){
            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setHttpMessage(HttpMessage.INVALID_EMAIL_OR_PASSWORD);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtAuthenticationResponse);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @GetMapping(path = "/verify-account")
    public ResponseEntity<Boolean> confirm(@RequestParam("token") String token){
        try {
            return ResponseEntity.ok(confirmationTokenService.confirmToken(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
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

    @GetMapping("reset-password")
    public ResponseEntity<String> resetPasswordGet(@RequestParam(required = true) String token) {

        try {
            boolean isValidPasswordResetRequest = passwordService.validPasswordResetRequest(token);

            if(!isValidPasswordResetRequest) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.ok("password return page");

        } catch (Exception e) {

            return ResponseEntity.badRequest().build();

        }
    }

    @Transactional
    @PostMapping("reset-password")
    public ResponseEntity<?> resetPasswordPost(
            @RequestParam(required = true) String token,
            @RequestBody(required = true) PasswordResetRequest passwordResetNewPasswordModel) {

        try {
            boolean success = passwordService.resetPassword(token, passwordResetNewPasswordModel);

            if(success) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
