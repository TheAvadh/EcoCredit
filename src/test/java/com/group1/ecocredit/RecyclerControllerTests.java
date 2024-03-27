package com.group1.ecocredit;

import com.group1.ecocredit.controllers.RecyclerController;
import com.group1.ecocredit.dto.DisplayBidRequest;
import com.group1.ecocredit.models.BidUser;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.AuctionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class RecyclerControllerTests {

    @Mock
    private AuctionService auctionService;
    @InjectMocks
    private RecyclerController recyclerController;
    private static Authentication authentication;
    private static SecurityContext securityContext;


    @BeforeEach
    public void setUp() {
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetUserBids_Success() {
        User user = new User();
        user.setId(1);
        authenticate(user);

        List<BidUser> expectedBids = new ArrayList<>();
        expectedBids.add(new BidUser());
        when(auctionService.viewUserBids(1)).thenReturn(expectedBids);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        assertEquals(expectedBids, responseEntity.getBody());
        verify(auctionService, times(1)).viewUserBids(1);
    }

    @Test
    public void testGetUserBids_NoBids() {
        User user = new User();
        user.setId(1);
        authenticate(user);

        List<BidUser> expectedBids = new ArrayList<>();
        when(auctionService.viewUserBids(1)).thenReturn(expectedBids);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_Unauthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(null);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(auctionService, never()).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_IllegalException() {
        User user = new User();
        user.setId(1);
        authenticate(user);

        when(auctionService.viewUserBids(anyInt())).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_Exception() {
        User user = new User();
        user.setId(1);
        authenticate(user);

        when(auctionService.viewUserBids(anyInt())).thenThrow(RuntimeException.class);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }

    @Test
    public void testGetAllActiveBids_Authenticated() {
        setAuthenticated(true);

        List<BidUser> expectedBids = new ArrayList<>();
        expectedBids.add(new BidUser());
        when(auctionService.viewAllActiveBids()).thenReturn(expectedBids);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBids, responseEntity.getBody());
        verify(auctionService, times(1)).viewAllActiveBids();
    }

    @Test
    public void testGetAllActiveBids_Unauthenticated() {
        setAuthenticated(false);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(auctionService, never()).viewAllActiveBids();
    }

    @Test
    public void testGetAllActiveBids_NoActiveBids() {
        setAuthenticated(true);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());
        verify(auctionService, times(1)).viewAllActiveBids();
    }

    @Test
    public void testGetAllActiveBids_IllegalArgumentException() {
        setAuthenticated(true);

        when(auctionService.viewAllActiveBids()).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewAllActiveBids();
    }


    @Test
    public void testGetAllActiveBids_Exception() {
        setAuthenticated(true);

        when(auctionService.viewAllActiveBids()).thenThrow(HttpClientErrorException.NotFound.class);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewAllActiveBids();
    }

    @Test
    public void testPlaceBid_Authenticated_Success() {
        setAuthenticated(true);

        BidUser expectedBidUser = new BidUser();
        when(auctionService.placeBid(1L)).thenReturn(expectedBidUser);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBidUser, responseEntity.getBody());
        verify(auctionService, times(1)).placeBid(1L);
    }

    @Test
    public void testPlaceBid_Authenticated_NotFound() {
        setAuthenticated(false);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(auctionService, never()).viewAllActiveBids();
    }

    @Test
    public void testPlaceBid_Unauthenticated() {
        setAuthenticated(false);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(auctionService, never()).placeBid(anyLong());
    }

    @Test
    public void testPlaceBid_RuntimeException() {
        setAuthenticated(true);

        when(auctionService.placeBid(1L)).thenThrow(RuntimeException.class);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());
        verify(auctionService, times(1)).placeBid(1L);
    }

    @Test
    public void testPlaceBid_IllegalArgumentException() {
        setAuthenticated(true);

        when(auctionService.placeBid(1L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(auctionService, times(1)).placeBid(1L);
    }

    @Test
    public void testRaiseBid_Authenticated() {
        User user = new User();
        authenticate(user);

        DisplayBidRequest request = new DisplayBidRequest();

        BidUser expectedBidUser = new BidUser();
        when(auctionService.raiseBid(any(DisplayBidRequest.class), any(User.class))).thenReturn(expectedBidUser);

        ResponseEntity<BidUser> responseEntity = recyclerController.raiseBid(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        verify(auctionService, times(1)).raiseBid(request, user);
    }

    @Test
    public void testRaiseBid_Unauthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(null);

        ResponseEntity<BidUser> responseEntity = recyclerController.raiseBid(new DisplayBidRequest());

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());
    }

    @Test
    public void testRaiseBid_NotFound() {
        User user = new User();
        authenticate(user);

        DisplayBidRequest request = new DisplayBidRequest();

        when(auctionService.raiseBid(request, user)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<BidUser> responseEntity = recyclerController.raiseBid(request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());
        verify(auctionService, times(1)).raiseBid(request, user);
    }

    @Test
    public void testRaiseBid_Exception() {
        User user = new User();
        authenticate(user);

        DisplayBidRequest request = new DisplayBidRequest();

        when(auctionService.raiseBid(any(DisplayBidRequest.class),
                any(User.class))).thenThrow(RuntimeException.class);

        ResponseEntity<BidUser> responseEntity = recyclerController.raiseBid(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());
        verify(auctionService, times(1)).raiseBid(request, user);
    }

    private void setAuthenticated(boolean isAuthenticated){
        when(authentication.isAuthenticated()).thenReturn(isAuthenticated);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    private void authenticate(User user){
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
}