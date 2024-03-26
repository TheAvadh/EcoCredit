package com.group1.ecocredit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import com.group1.ecocredit.controllers.AuthenticationController;
import com.group1.ecocredit.dto.*;
import com.group1.ecocredit.enums.HttpMessage;
import com.group1.ecocredit.models.ConfirmationToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.ConfirmationTokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.AuthenticationService;

import com.group1.ecocredit.services.ConfirmationTokenService;
import com.group1.ecocredit.services.PasswordService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

public class AuthControllerTest {

    private AuthenticationController authenticationController;
    private AuthenticationService authenticationServiceMock;
    private ConfirmationTokenService confirmationTokenServiceMock;
    private ConfirmationTokenRepository confirmationTokenRepositoryMock;
    private UserRepository userRepository;
    private PasswordService passwordServiceMock;
    private PasswordEncoder passwordEncoder;

    private static String VALID_EMAIL = "VALID_EMAIL@EMAIL.COM";
    private static String INVALID_EMAIL = "INVALID_EMAIL@EMAIL.COM";
    private static String VALID_PASSWORD = "VALID_PASSWORD";
    private static String INVALID_PASSWORD = "INVALID_PASSWORD";
    private static String VALID_TOKEN ="VALID_TOKEN";
    private static String INVALID_TOKEN="INVALID_TOKEN";

    private static String ENCODED_PASSWORD = "ENCODED_PASSWORD";
    private static String URL = "http://localhost:8080";
    private static SignUpRequest.AddressBD address = new SignUpRequest.AddressBD();

    @PostConstruct
    public void init() {
        address.setCity("Halifax");
        address.setProvince("NS");
        address.setStreet("Random street");
        address.setPostalCode("B3K 2B3");
    }


    @Before
    public void setUp() {

        confirmationTokenServiceMock = mock(ConfirmationTokenService.class);
        authenticationServiceMock = mock(AuthenticationService.class);
        confirmationTokenRepositoryMock = mock(ConfirmationTokenRepository.class);
        userRepository = mock(UserRepository.class);
        passwordServiceMock = mock(PasswordService.class);
        passwordEncoder = mock(PasswordEncoder.class);

        authenticationController = new AuthenticationController(URL, authenticationServiceMock, passwordServiceMock, confirmationTokenServiceMock);
    }

    @Test
    public void testSignup_Successful() throws MessagingException {
        SignUpRequest signUpRequest = new SignUpRequest();

        JwtAuthenticationResponse mockedResponse = new JwtAuthenticationResponse();
        when(authenticationServiceMock.signup(signUpRequest)).thenReturn(mockedResponse);

        ResponseEntity<JwtAuthenticationResponse> responseEntity = authenticationController.signup(signUpRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockedResponse, responseEntity.getBody());
    }

    @Test
    public void testSignupUnsuccessfulEmailFailure() throws MessagingException {
        SignUpRequest signUpRequest = new SignUpRequest();

        signUpRequest.setEmail(VALID_EMAIL);
        signUpRequest.setPassword(VALID_PASSWORD);

        signUpRequest.setAddress(address);

        when(authenticationServiceMock.signup(signUpRequest)).thenThrow(MessagingException.class);

        ResponseEntity<JwtAuthenticationResponse> responseEntity = authenticationController.signup(signUpRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testSignIn_Successful() {
        SignInRequest signInRequest = new SignInRequest(VALID_EMAIL, VALID_PASSWORD);

        JwtAuthenticationResponse mockedResponse = new JwtAuthenticationResponse();
        when(authenticationServiceMock.signIn(signInRequest)).thenReturn(mockedResponse);

        ResponseEntity<JwtAuthenticationResponse> responseEntity = authenticationController.signin(signInRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockedResponse, responseEntity.getBody());
    }

    @Test
    public void testSignInUnsuccessfulInvalidEmailOrPassword() {
        SignInRequest signInRequest = new SignInRequest(INVALID_EMAIL, INVALID_PASSWORD);


        JwtAuthenticationResponse mockedResponse = new JwtAuthenticationResponse();
        mockedResponse.setHttpMessage(HttpMessage.INVALID_EMAIL_OR_PASSWORD);
        when(authenticationServiceMock.signIn(signInRequest)).thenReturn(mockedResponse);

        ResponseEntity<JwtAuthenticationResponse> responseEntity = authenticationController.signin(signInRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(Objects.requireNonNull(responseEntity.getBody()).getToken());
        assertEquals(mockedResponse, responseEntity.getBody());
    }

    @Test
    public void testConfirmAccountSuccessful() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        User user = new User();
        user.setEmail(VALID_EMAIL);
        confirmationToken.setUser(user);

        when(confirmationTokenServiceMock.confirmToken(VALID_TOKEN)).thenReturn(true);
        when(confirmationTokenRepositoryMock.findByToken(VALID_TOKEN)).thenReturn(Optional.of(confirmationToken));
        when(userRepository.findByEmail(confirmationToken.getUser().getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<Boolean> responseEntity = authenticationController.confirm(VALID_TOKEN);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testForgetPasswordSuccessful() {
        ForgetPasswordRequest request = new ForgetPasswordRequest();
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        request.setEmail(VALID_EMAIL);
        User user = new User();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordServiceMock.forgetPassword(request)).thenReturn(true);
        when(passwordEncoder.encode(passwordResetRequest.getNewPassword())).thenReturn(ENCODED_PASSWORD);

        ResponseEntity<?> responseEntity = authenticationController.forgetPassword(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testForgetPasswordUnsuccessful() {
        ForgetPasswordRequest request = new ForgetPasswordRequest();
        request.setEmail(INVALID_EMAIL);

        when(passwordServiceMock.forgetPassword(request)).thenReturn(false);

        ResponseEntity<?> responseEntity = authenticationController.forgetPassword(request);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void testResetPasswordGetSuccessful() {
        String token = "valid_token";

        when(passwordServiceMock.validPasswordResetRequest(token)).thenReturn(true);

        ResponseEntity<Void> responseEntity = authenticationController.resetPasswordGet(token);

        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(URI.create("http://localhost:8080/reset-password?token=valid_token"), responseEntity.getHeaders().getLocation());
    }

    @Test
    public void testResetPasswordGetUnsuccessfulInvalidToken() {

        when(passwordServiceMock.validPasswordResetRequest(INVALID_TOKEN)).thenReturn(false);

        ResponseEntity<Void> responseEntity = authenticationController.resetPasswordGet(INVALID_TOKEN);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void testResetPasswordPostSuccessful() {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();

        when(passwordServiceMock.resetPassword(VALID_TOKEN, passwordResetRequest)).thenReturn(true);

        ResponseEntity<?> responseEntity = authenticationController.resetPasswordPost(VALID_TOKEN, passwordResetRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testResetPasswordPost_Unsuccessful() {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();

        when(passwordServiceMock.resetPassword(VALID_TOKEN, passwordResetRequest)).thenReturn(false);

        ResponseEntity<?> responseEntity = authenticationController.resetPasswordPost(VALID_TOKEN, passwordResetRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }



    @ExceptionHandler(MessagingException.class)
    public void ExceptionHandling(Exception e) {
        System.out.println(e.getMessage());
    }
}
