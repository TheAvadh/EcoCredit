package com.group1.ecocredit.AuthenticationServiceTests;


import com.group1.ecocredit.dto.JwtAuthenticationResponse;
import com.group1.ecocredit.dto.RefreshTokenRequest;
import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.implementations.AuthenticationServiceImpl;
import com.group1.ecocredit.services.implementations.JWTServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenTests {
    @Mock
    private JWTServiceImpl jwtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User user;
    private String validRefreshToken = "validRefreshToken";
    private String newJwtToken = "newJwtToken";
    private String userEmail = "user@example.com";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail(userEmail);
        user.setRole(Role.valueOf("USER"));
    }
    @Test
    void refreshToken_Success() {
        // Arrange
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(validRefreshToken);

        when(jwtService.extractUserName(validRefreshToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(validRefreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn(newJwtToken);

        // Act
        JwtAuthenticationResponse response = authenticationService.refreshToken(refreshTokenRequest);

        // Assert
        assertNotNull(response);
        assertEquals(newJwtToken, response.getToken());
        assertEquals(validRefreshToken, response.getRefreshToken());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    void refreshToken_Failure_InvalidToken() {
        // Arrange
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(validRefreshToken);

        when(jwtService.extractUserName(validRefreshToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(validRefreshToken, user)).thenReturn(false);

        // Act
        JwtAuthenticationResponse response = authenticationService.refreshToken(refreshTokenRequest);

        // Assert
        assertNull(response, "Expected null response when token is invalid");
    }
}
