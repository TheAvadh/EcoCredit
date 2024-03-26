package com.group1.ecocredit.AuthenticationServiceTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.group1.ecocredit.dto.JwtAuthenticationResponse;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.implementations.AuthenticationServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.group1.ecocredit.dto.SignUpRequest;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.AuthenticationService;
import com.group1.ecocredit.services.ConfirmationTokenService;
import com.group1.ecocredit.services.JWTService;
import com.group1.ecocredit.services.implementations.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class SignUpTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailServiceImpl emailServiceImpl;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private SignUpRequest signUpRequest;

    @ExtendWith(MockitoExtension.class)

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setFirstName("Test");
        signUpRequest.setLastName("User");
        signUpRequest.setPassword("password123");

        // Assuming SignUpRequest has an inner class or a method to set address directly
        SignUpRequest.AddressBD addressBD = new SignUpRequest.AddressBD();
        addressBD.setStreet("123 Main St");
        addressBD.setCity("Anytown");
        addressBD.setProvince("Anystate");
        addressBD.setPostalCode("12345");
        signUpRequest.setAddress(addressBD); // Assuming this is the correct method to set the address

        lenient().when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        lenient().when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        lenient().when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        lenient().when(jwtService.generateRefreshToken(anyMap(), any(User.class))).thenReturn("refreshToken");

    }

    @Test
    void testSignupUserCreationAndPersistence() throws MessagingException {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);

        verify(userRepository).save(any(User.class));
        assertNotNull(response);
        // Additional assertions for user details
    }

    @Test
    void testPasswordEncoding() {
        authenticationService.signup(signUpRequest);

        verify(passwordEncoder).encode("password123");
    }

    @Test
    void testJwtAndRefreshTokenGeneration() {
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(anyMap(), any(User.class))).thenReturn("refreshToken");

        JwtAuthenticationResponse response = null;
        response = authenticationService.signup(signUpRequest);

        assertEquals("jwtToken", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }
}
