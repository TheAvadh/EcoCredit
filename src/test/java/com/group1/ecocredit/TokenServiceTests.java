package com.group1.ecocredit;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
}

