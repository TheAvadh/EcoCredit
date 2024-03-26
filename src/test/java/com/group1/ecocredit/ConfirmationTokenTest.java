package com.group1.ecocredit;


import com.google.common.hash.Hashing;
import com.group1.ecocredit.models.ConfirmationToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.ConfirmationTokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.ConfirmationTokenService;
import com.group1.ecocredit.services.implementations.ConfirmationTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*@TestPropertySource(properties = {
        "signup.verification.token.hours=24" // Set the value to whatever you want for testing
})
@SpringBootTest
@ContextConfiguration*/
public class ConfirmationTokenTest {


    @InjectMocks
    private ConfirmationTokenServiceImpl confirmationTokenService;
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    private UserRepository userRepository;
    int userId = 1;
    int validityInHours = 5000;
    String token = "3478e41e-59d4-4d3c-afe5-8ff5195cd8ff";
    private String hashedToken;
    private ConfirmationToken confirmationToken;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        confirmationTokenService = new ConfirmationTokenServiceImpl(confirmationTokenRepository, userRepository);

        /*validityInHours = Integer.parseInt(env.getProperty("signup.verification.token.hours"));*/
    }


    @BeforeEach
    public void init(){
        User user = new User();
        user.setId(1);
        user.setFirstName("Chandler");
        user.setLastName("Bing");
        user.setEmail("chandler.bing@gmail.com");
        user.setPhoneNumber("1234567890");

        hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();

        assertNotNull(hashedToken);

        confirmationToken = ConfirmationToken
                .builder()
                .token(hashedToken)
                .createdTime(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusHours(validityInHours))
                .user(user)
                .build();

        assertNotNull(confirmationToken);


    }
    @Test
    @DisplayName("This should generate token")
    public void genrerateTokenTest(){
        String token = confirmationTokenService.generateConfirmationToken(userId);
        assertNotNull(token);
        assertTrue(token.length()>1);
    }

    /*@Test
    @DisplayName("This should save confirmation token")
    public void saveConfirmationTokenTest() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Wick");
        user.setEmail("JohnWick@kmail.com");
        user.setPhoneNumber("1234567890");

        String token = "3478e41e-59d4-4d3c-afe5-8ff5195cd8ff";

        when(confirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(new ConfirmationToken());

        String savedToken = confirmationTokenService.saveConfirmationToken(token, user);

        assertNotNull(savedToken);
        assertEquals(token, savedToken);

        verify(confirmationTokenRepository).save(any(ConfirmationToken.class));
    }*/


    @Test
    @DisplayName("This should return true when token confirms")
    public void confirmTokenTest(){

        when(confirmationTokenRepository.findByToken(hashedToken)).thenReturn(Optional.of(confirmationToken));
        when(userRepository.findByEmail(confirmationToken.getUser().getEmail())).thenReturn(Optional.of(confirmationToken.getUser()));


        assertTrue(confirmationTokenService.confirmToken(token));

        verify(confirmationTokenRepository).save(any(ConfirmationToken.class));
        verify(userRepository).save(any(User.class));

    }

    @Test
    @DisplayName("This should return false when token is not found")
    public void confirmTokenTokenNotFoundTest(){

        when(confirmationTokenRepository.findByToken(hashedToken)).thenReturn(Optional.empty());

        boolean result = confirmationTokenService.confirmToken(token);

        assertFalse(result);

    }

    @Test
    @DisplayName("This should return false when user is not found for the token")
    public void confirmationTokenUserNotFoundTest() {
        when(confirmationTokenRepository.findByToken(anyString())).thenReturn(Optional.of(confirmationToken));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertFalse(confirmationTokenService.confirmToken(token));
    }

    @Test
    @DisplayName("This should return true if token is valid")
    public void isValidTokenValidTest() {

        ConfirmationToken validToken = ConfirmationToken.builder()
                .token(hashedToken)
                .expirationTime(LocalDateTime.now().plusHours(1))
                .build();

        assertTrue(confirmationTokenService.isValidToken(validToken));
    }

}
