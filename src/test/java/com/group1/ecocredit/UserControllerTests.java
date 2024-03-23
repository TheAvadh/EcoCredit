package com.group1.ecocredit;

import com.group1.ecocredit.controllers.UserController;
import com.group1.ecocredit.dto.UpdateProfileRequest;
import com.group1.ecocredit.dto.UpdateProfileResponse;
import com.group1.ecocredit.dto.UserDetailsResponse;
import com.group1.ecocredit.services.JWTService;
import com.group1.ecocredit.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserControllerTests {
    @Mock
    private UserService userService;
    @Mock
    private JWTService jwtService;
    @InjectMocks
    private UserController userController;
    private static UpdateProfileResponse updateProfileResponse;
    private static UpdateProfileRequest updateProfileRequest;
    private static Authentication authentication;
    private static SecurityContext securityContext;
    private static HttpServletRequest httpServletRequest;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN = "Bearer test_token";
    private static UserDetailsResponse userDetailsResponse;

    @BeforeAll
    static void setUp() {
        updateProfileResponse = new UpdateProfileResponse();
        updateProfileRequest = new UpdateProfileRequest();

        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        httpServletRequest = mock(HttpServletRequest.class);

        userDetailsResponse = new UserDetailsResponse();
    }

    @Test
    public void testUpdateProfile_Success() {
        updateProfileResponse.setResponse(UpdateProfileResponse.ResponseType.SUCCESS);

        when(userService.updateProfile(updateProfileRequest)).thenReturn(updateProfileResponse);

        var response = userController.updateProfile(updateProfileRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateProfile_UserNotFound() {
        updateProfileResponse.setResponse(UpdateProfileResponse.ResponseType.USER_NOT_FOUND);

        when(userService.updateProfile(updateProfileRequest)).thenReturn(updateProfileResponse);

        var response = userController.updateProfile(updateProfileRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testUpdateProfile_InternalServerError() {
        updateProfileResponse.setResponse(UpdateProfileResponse.ResponseType.INTERNAL_SERVER_ERROR);

        when(userService.updateProfile(updateProfileRequest)).thenReturn(updateProfileResponse);

        var response = userController.updateProfile(updateProfileRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn(TOKEN);

        String userIdString = "1";
        int userIdInt = 1;

        when(jwtService.extractUserID(httpServletRequest.getHeader(AUTHORIZATION_HEADER))).thenReturn(userIdString);
        when(userService.getUserById(userIdInt)).thenReturn(userDetailsResponse);

        var response = userController.getUserById(httpServletRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(jwtService, times(1)).extractUserID(anyString());
        verify(userService, times(1)).getUserById(anyInt());
    }

    @Test
    public void testGetUserById_NotAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        var response = userController.getUserById(httpServletRequest);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(jwtService, never()).extractUserID(anyString());
        verify(userService, never()).getUserById(anyInt());
    }

    @Test
    public void testGetUserById_UserNotFound() throws Exception {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn(TOKEN);

        String userIdString = "1";
        int userIdInt = 1;

        when(jwtService.extractUserID(httpServletRequest.getHeader(AUTHORIZATION_HEADER))).thenReturn(userIdString);
        when(userService.getUserById(userIdInt)).thenReturn(null);

        var response = userController.getUserById(httpServletRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(jwtService, times(1)).extractUserID(anyString());
        verify(userService, times(1)).getUserById(anyInt());
    }
}
