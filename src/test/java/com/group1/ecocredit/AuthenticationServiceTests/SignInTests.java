package com.group1.ecocredit.AuthenticationServiceTests;



import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.group1.ecocredit.dto.JwtAuthenticationResponse;
import com.group1.ecocredit.dto.SignInRequest;
import com.group1.ecocredit.enums.HttpMessage;
import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.JWTService;
import com.group1.ecocredit.services.implementations.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SignInTests {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private SignInRequest signInRequest;
    private User user;

    @BeforeEach
    void setUp() {
        signInRequest = new SignInRequest("user@example.com","password");
        user = new User();
        user.setEmail(signInRequest.getEmail());
        user.setId(1);
        user.setRole(Role.valueOf("USER"));
    }

    @Test
    void signInSuccess() {
        // Arrange
        when(userRepository.findByEmail(signInRequest.getEmail()))
                .thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(new HashMap<>(), user)).thenReturn("refreshToken");

        // Act
        JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals(HttpMessage.SUCCESS, response.getHttpMessage());
        assertEquals(user.getRole(), response.getRole());
        assertEquals(user.getId(), response.getUserId());
    }

    @Test
    void signInFailureDueToBadCredentials() {
        // Arrange
        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authenticationService.signIn(signInRequest));
    }

    @Test
    void signInWithInvalidPassword() {
        // Arrange
        lenient().when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.of(user));
        doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), "wrongPassword"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () ->
                        authenticationService.signIn(new SignInRequest(signInRequest.getEmail(), "wrongPassword")),
                "Expected signIn to throw due to invalid password, but it didn't"
        );
    }

    @Test
    void signInWithInvalidEmail() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                        authenticationService.signIn(new SignInRequest("nonexistent@example.com", "password")),
                "Expected signIn to throw, but it didn't"
        );

        assertTrue(exception.getMessage().contains("Invalid email or password."));
    }



}
