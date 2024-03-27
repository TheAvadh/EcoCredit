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




    public void getUserBid_auth(){
        User user = new User();
        user.setId(1);
        BidUser bidUser = new BidUser();
        bidUser.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}