package com.group1.ecocredit;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.common.hash.Hashing;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.PasswordResetTokenRepository;
import com.group1.ecocredit.services.implementations.EmailServiceImpl;
import com.group1.ecocredit.services.implementations.TokenServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTests {
    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private EmailServiceImpl emailService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        ReflectionTestUtils.setField(tokenService, "validityInHours", 24);

    }
    @Test
    void generatePasswordResetToken_ShouldReturnToken() {
        String token = tokenService.generatePasswordResetToken(user.getId());

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void isValidToken_ShouldReturnTrueForValidToken() {
        PasswordResetToken token = new PasswordResetToken();
        token.setExpirationTime(LocalDateTime.now().plusHours(1));

        assertTrue(tokenService.isValidToken(token));
    }

    @Test
    void isValidToken_ShouldReturnFalseForExpiredToken() {
        PasswordResetToken token = new PasswordResetToken();
        token.setExpirationTime(LocalDateTime.now().minusHours(1));

        assertFalse(tokenService.isValidToken(token));
    }

    @Test
    void inValidateToken_ShouldInvalidateToken() {
        String token = "dummyToken";
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUsed(false);

        when(passwordResetTokenRepository.findByToken(anyString())).thenReturn(passwordResetToken);

        tokenService.inValidateToken(token);

        assertTrue(passwordResetToken.isUsed());
        verify(passwordResetTokenRepository).save(passwordResetToken);
    }

    @Test
    void savePasswordResetToken_ShouldCorrectlySaveToken() {
        String rawToken = "dummyToken";
        ArgumentCaptor<PasswordResetToken> tokenCaptor = ArgumentCaptor.forClass(PasswordResetToken.class);

        tokenService.savePasswordResetToken(rawToken, user);

        verify(passwordResetTokenRepository).save(tokenCaptor.capture());
        PasswordResetToken savedToken = tokenCaptor.getValue();

        assertNotNull(savedToken);
        assertEquals(user, savedToken.getUser());
        assertFalse(savedToken.isUsed());
        assertNotNull(savedToken.getExpirationTime());
        assertTrue(savedToken.getExpirationTime().isAfter(LocalDateTime.now()));

        // Assuming Utils.hash or similar hashing is idempotent and predictable for testing
        String expectedHashedToken = Hashing.sha256().hashString(rawToken, StandardCharsets.UTF_8).toString();
        assertEquals(expectedHashedToken, savedToken.getToken());
    }
    @Test
    void savePasswordResetToken_ShouldCorrectlySaveTokenWithHashedValueAndExpiration() {
        // Given
        String rawToken = "dummyToken";
        LocalDateTime beforeSaveTime = LocalDateTime.now();

        // Using ArgumentCaptor to capture the PasswordResetToken object when save() is called
        ArgumentCaptor<PasswordResetToken> tokenCaptor = ArgumentCaptor.forClass(PasswordResetToken.class);

        // When
        tokenService.savePasswordResetToken(rawToken, user);

        // Then
        verify(passwordResetTokenRepository).save(tokenCaptor.capture());
        PasswordResetToken capturedToken = tokenCaptor.getValue();

        assertNotNull(capturedToken);
        assertEquals(user, capturedToken.getUser());
        assertFalse(capturedToken.isUsed());
        assertTrue(capturedToken.getExpirationTime().isAfter(beforeSaveTime.plusHours(23)),
                "The token expiration time should be greater than the current time plus the validity duration minus a small delta.");
        assertTrue(capturedToken.getExpirationTime().isBefore(beforeSaveTime.plusHours(25)),
                "The token expiration time should be less than the current time plus the validity duration plus a small delta.");

        // Verifying the token is hashed correctly
        String expectedHashedToken = Hashing.sha256().hashString(rawToken, StandardCharsets.UTF_8).toString();
        assertEquals(expectedHashedToken, capturedToken.getToken(), "The saved token should be hashed.");
    }

}

