package com.group1.ecocredit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.group1.ecocredit.services.UserService;
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
import com.group1.ecocredit.services.WalletService;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"signup.verification.token.hours=24"})
public class ConfirmationServiceTokenTests{
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    private UserService userService;

    @Mock
    private WalletService walletService;

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
        ReflectionTestUtils.setField(confirmationTokenService, "validityInHours", 24); // Assuming 24 hours for the example

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

    @Test
    void saveConfirmationToken_ShouldSaveTokenSuccessfully() {
        when(confirmationTokenRepository.save(any(ConfirmationToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String returnedToken = confirmationTokenService.saveConfirmationToken(token, user);
        assertEquals(token, returnedToken);
        verify(confirmationTokenRepository).save(any(ConfirmationToken.class));
    }

    @Test
    void confirmToken_WithValidToken_ShouldEnableUserAndCreateWallet() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(user);
        confirmationToken.setToken(token);
        confirmationToken.setExpirationTime(LocalDateTime.now().plusHours(2));
        confirmationToken.setUsed(false);

        when(confirmationTokenRepository.findByToken(anyString())).thenReturn(Optional.of(confirmationToken));
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(user));

        boolean result = confirmationTokenService.confirmToken(token);

        assertTrue(result);
        assertTrue(user.isEnabled());
        verify(userService).save(any(User.class));
        verify(walletService).createWalletForUser(any(User.class));
    }

    @Test
    void confirmToken_WithInvalidToken_ShouldReturnFalse() {
        when(confirmationTokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        boolean result = confirmationTokenService.confirmToken(token);

        assertFalse(result);
    }

    @Test
    void confirmToken_WithExpiredToken_ShouldReturnFalse() {
        ConfirmationToken expiredToken = new ConfirmationToken();
        expiredToken.setExpirationTime(LocalDateTime.now().minusHours(1));
        expiredToken.setToken(token);
        expiredToken.setUser(user);
        when(confirmationTokenRepository.findByToken(anyString())).thenReturn(Optional.of(expiredToken));

        boolean result = confirmationTokenService.confirmToken(token);

        assertFalse(result);
    }

    @Test
    void confirmToken_WhenUserNotFound_ShouldReturnFalse() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(user);
        confirmationToken.setToken(token);
        confirmationToken.setExpirationTime(LocalDateTime.now().plusHours(2));

        when(confirmationTokenRepository.findByToken(anyString())).thenReturn(Optional.of(confirmationToken));
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());

        boolean result = confirmationTokenService.confirmToken(token);

        assertFalse(result);
    }


}
