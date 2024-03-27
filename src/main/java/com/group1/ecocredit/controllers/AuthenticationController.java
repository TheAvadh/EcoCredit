package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.*;
import com.group1.ecocredit.dto.PasswordResetRequest;
import com.group1.ecocredit.enums.HttpMessage;
import com.group1.ecocredit.services.AuthenticationService;
import com.group1.ecocredit.services.PasswordService;
import com.group1.ecocredit.services.ConfirmationTokenService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthenticationController {

    private final String frontendServerUrl;
    private final AuthenticationService authenticationService;
    private final PasswordService passwordService;
    private final ConfirmationTokenService confirmationTokenService;

    public AuthenticationController(@Value("${front-end.server.url}") String frontendServerUrl,
                                    AuthenticationService authenticationService,
                                    PasswordService passwordService,
                                    ConfirmationTokenService confirmationTokenService) {
        this.frontendServerUrl = frontendServerUrl;
        this.authenticationService = authenticationService;
        this.passwordService = passwordService;
        this.confirmationTokenService = confirmationTokenService;
    }

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

    @GetMapping(path = "/verify-account")
    public ResponseEntity<Boolean> confirm(@RequestParam("token") String token){
        try {
            var success = confirmationTokenService.confirmToken(token);
            if (!success) {
                return ResponseEntity.status((HttpStatus.UNAUTHORIZED)).build();
            }
            return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> resetPasswordGet(@RequestParam(required = true) String token) {

        try {
            boolean isValidPasswordResetRequest = passwordService.validPasswordResetRequest(token);

            if(!isValidPasswordResetRequest) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Redirect to frontend Reset Password page
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(
                    "%s/reset-password?token=%s"
                            .formatted(frontendServerUrl, token))).build();
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
