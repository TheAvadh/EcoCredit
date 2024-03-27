package com.group1.ecocredit;

import com.group1.ecocredit.controllers.RecyclerController;
import com.group1.ecocredit.dto.DisplayBidRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.BidUser;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.AuctionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecyclerControllerTests {

    @Mock
    private AuctionService auctionService;

    @InjectMocks
    private RecyclerController recyclerController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserBids_Success() {

        getUserBid_auth();

        List<BidUser> expectedBids = new ArrayList<>();
        expectedBids.add(new BidUser());
        when(auctionService.viewUserBids(1)).thenReturn(expectedBids);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedBids, responseEntity.getBody());
        verify(auctionService, times(1)).viewUserBids(1);
    }

    @Test
    public void testGetUserBids_NoBids() {
        getUserBid_auth();

        List<BidUser> expectedBids = new ArrayList<>();
        when(auctionService.viewUserBids(1)).thenReturn(expectedBids);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_Unauthenticated() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(auctionService, never()).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_IllegalException() {
        getUserBid_auth();

        when(auctionService.viewUserBids(anyInt())).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_Exception() {
        getUserBid_auth();

        when(auctionService.viewUserBids(anyInt())).thenThrow(RuntimeException.class);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }

    @Test
    public void testGetAllActiveBids_Authenticated() {

        authenticated();

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
        is_authenticated(false);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(auctionService, never()).viewAllActiveBids();
    }

    @Test
    public void testGetAllActiveBids_NoActiveBids() {

        is_authenticated(true);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(auctionService, times(1)).viewAllActiveBids();
    }

    @Test
    public void testGetAllActiveBids_IllegalArgumentException() {
        is_authenticated(true);

        when(auctionService.viewAllActiveBids()).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewAllActiveBids();
    }


    @Test
    public void testGetAllActiveBids_Exception() {
        is_authenticated(true);

        when(auctionService.viewAllActiveBids()).thenThrow(HttpClientErrorException.NotFound.class);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewAllActiveBids();
    }

    @Test
    public void testPlaceBid_Authenticated_Success() {

        User user = new User();
        Bid bid = new Bid();
        bid.setId(1L);

        authenticated();

        BidUser expectedBidUser = new BidUser();
        when(auctionService.placeBid(1L)).thenReturn(expectedBidUser);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBidUser, responseEntity.getBody());
        verify(auctionService, times(1)).placeBid(1L);
    }

    @Test
    public void testPlaceBid_Authenticated_NotFound() {

        is_authenticated(false);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(auctionService, never()).viewAllActiveBids();
    }

    @Test
    public void testPlaceBid_Unauthenticated() {
        is_authenticated(false);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(auctionService, never()).placeBid(anyLong());
    }

    @Test
    public void testPlaceBid_RuntimeException() {
        User user = new User();
        Bid bid = new Bid();
        bid.setId(1L);
        authenticated();

        when(auctionService.placeBid(1L)).thenThrow(RuntimeException.class);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(auctionService, times(1)).placeBid(1L);
    }

    @Test
    public void testPlaceBid_IllegalArgumentException() {
        User user = new User();
        Bid bid = new Bid();
        bid.setId(1L);
        authenticated();

        when(auctionService.placeBid(1L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<BidUser> responseEntity = recyclerController.placeBid(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(auctionService, times(1)).placeBid(1L);
    }




    @Test
    public void testRaiseBid_Authenticated() {
        User user = new User();
        authenticated();

        DisplayBidRequest request = new DisplayBidRequest();

        BidUser expectedBidUser = new BidUser();
        when(auctionService.raiseBid(request, user)).thenReturn(expectedBidUser);

        ResponseEntity<BidUser> responseEntity = recyclerController.raiseBid(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(auctionService, times(1)).raiseBid(request, user);
    }

    @Test
    public void testRaiseBid_Unauthenticated() {
        is_authenticated(false);

        ResponseEntity<BidUser> responseEntity = recyclerController.raiseBid(new DisplayBidRequest());

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testRaiseBid_NotFound() {
        User user = new User();
        authenticated();

        DisplayBidRequest request = new DisplayBidRequest();

        when(auctionService.raiseBid(request, user)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<BidUser> responseEntity = recyclerController.raiseBid(request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(auctionService, times(1)).raiseBid(request, user);
    }

    @Test
    public void testRaiseBid_Exception() {
        User user = new User();
        authenticated();

        DisplayBidRequest request = new DisplayBidRequest();

        when(auctionService.raiseBid(request, user)).thenThrow(RuntimeException.class);

        ResponseEntity<BidUser> responseEntity = recyclerController.raiseBid(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(auctionService, times(1)).raiseBid(request, user);
    }


    public void authenticated(){
        User user = new User();

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void is_authenticated(boolean is_auth){
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(is_auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void getUserBid_auth(){
        User user = new User();
        user.setId(1);
        BidUser bidUser = new BidUser();
        bidUser.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}