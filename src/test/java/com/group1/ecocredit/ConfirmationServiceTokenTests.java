package com.group1.ecocredit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.group1.ecocredit.services.implementations.ConfirmationTokenServiceImpl;
import com.group1.ecocredit.models.ConfirmationToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.ConfirmationTokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ConfirmationServiceTokenTests{
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConfirmationTokenServiceImpl confirmationTokenService;

    private User user;
    private String token;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setEnabled(false);

        token = "test-token";
    }

    @Test
    void generateConfirmationToken_ShouldGenerateToken() {
        String generatedToken = confirmationTokenService.generateConfirmationToken(user.getId());
        assertNotNull(generatedToken);
        assertFalse(generatedToken.isEmpty());
    }

    @Test
    void isValidToken_ShouldReturnTrueForValidToken() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setExpirationTime(LocalDateTime.now().plusHours(1));

        assertTrue(confirmationTokenService.isValidToken(confirmationToken));
    }

    @Test
    void isValidToken_ShouldReturnFalseForExpiredToken() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setExpirationTime(LocalDateTime.now().minusHours(1));

        assertFalse(confirmationTokenService.isValidToken(confirmationToken));
    }


}
