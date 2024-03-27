package com.group1.ecocredit;

import com.group1.ecocredit.dto.ForgetPasswordRequest;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.PasswordService;
import com.group1.ecocredit.services.TokenService;
import com.group1.ecocredit.services.implementations.PasswordServiceImpl;
import com.group1.ecocredit.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.group1.ecocredit.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordResetTokenServiceTests {

    private static PasswordService passwordService;

    private static PasswordResetTokenRepository passwordResetTokenRepository;

    private static TokenService tokenService;

    private static UserService userRepository;

    private static EmailService emailService;

    @BeforeEach
    public void setUp() {
        passwordResetTokenRepository = Mockito.mock(PasswordResetTokenRepository.class);
        tokenService = Mockito.mock(TokenService.class);
        userRepository = Mockito.mock(UserService.class);
        emailService = Mockito.mock(EmailService.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

        passwordService = new PasswordServiceImpl(userRepository,
                tokenService,
                emailService,
                passwordResetTokenRepository,
                passwordEncoder);
    }

    @Test
    public void testForgetPassword_ValidEmail_Success() {

        User user = new User();
        String validEmail = "vaid@email.com";
        String token = "generated_token";

        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
        forgetPasswordRequest.setEmail(validEmail);

        when(userRepository.findByEmail(forgetPasswordRequest.getEmail())).thenReturn(Optional.of(user));
        when(tokenService.generatePasswordResetToken(user.getId())).thenReturn(token);

        try {

            doNothing().when(emailService).sendResetPasswordEmail(validEmail, token);

        } catch (Exception ignored) {

        }

        Assertions.assertTrue(passwordService.forgetPassword(forgetPasswordRequest));

        Mockito.verify(tokenService).savePasswordResetToken(token, user);
    }

    @Test
    public void testForgetPassword_InvalidEmail() {

        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
        forgetPasswordRequest.setEmail("invalid@email.com");

        Assertions.assertFalse(passwordService.forgetPassword(forgetPasswordRequest));
    }


    @Test
    public void testValidPasswordResetRequest_ValidToken_ReturnsTrue() {
        // Arrange
        String validToken = "valid_token";
        String hashedToken = Utils.hash(validToken);
        PasswordResetToken mockToken = Mockito.mock(PasswordResetToken.class);
        Mockito.when(passwordResetTokenRepository.findByToken(hashedToken)).thenReturn(mockToken);
        Mockito.when(tokenService.isValidToken(mockToken)).thenReturn(true);

        // Act
        boolean result = passwordService.validPasswordResetRequest(validToken);

        // Assert
        Assertions.assertTrue(result);
        Mockito.verify(passwordResetTokenRepository).findByToken(hashedToken);
        Mockito.verify(tokenService).isValidToken(mockToken);
    }

    @Test
    public void testValidPasswordResetRequest_InvalidToken_ReturnsFalse() {
        // Arrange
        String invalidToken = "invalid_token";
        Mockito.when(passwordResetTokenRepository.findByToken(Mockito.anyString())).thenReturn(null);

        // Act
        boolean result = passwordService.validPasswordResetRequest(invalidToken);

        // Assert
        Assertions.assertFalse(result);
        Mockito.verify(passwordResetTokenRepository).findByToken(Utils.hash(invalidToken));
        Mockito.verify(tokenService, Mockito.never()).isValidToken(Mockito.any(PasswordResetToken.class));
    }
}
