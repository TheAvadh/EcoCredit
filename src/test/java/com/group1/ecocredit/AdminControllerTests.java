package com.group1.ecocredit;

import com.group1.ecocredit.controllers.AdminController;
import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.dto.admin.PickupAdminResponse;
import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.BidService;
import com.group1.ecocredit.services.admin.PickupAdminService;
import com.group1.ecocredit.services.admin.implementations.PickupAdminServiceImpl;
import com.group1.ecocredit.services.admin.implementations.WasteServiceImpl;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AdminControllerTests {

    @Mock
    private BidService bidServiceMock;

    @Mock
    private PickupAdminServiceImpl pickupAdminServiceMock;
    @Mock
    private WasteServiceImpl wasteServiceMock;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPutWasteForBid_SuccessfulExecution() {

        BidCreateRequest request = new BidCreateRequest();
        Bid expectedBid = new Bid();
        when(bidServiceMock.putWasteForBid(request)).thenReturn(expectedBid);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<Bid> response = adminController.putWasteForBid(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBid, response.getBody());
    }

    @Test
    public void testPutWasteForBid_UnauthorizedUser() {

        BidCreateRequest request = new BidCreateRequest();
        setAuthenticatedUserWithRole(Role.USER);

        ResponseEntity<Bid> response1 = adminController.putWasteForBid(request);
        ResponseEntity<List<Bid>> response2 = adminController.getAllActiveBids();
        ResponseEntity<List<Bid>> response3 = adminController.getAllBids();

        assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, response2.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, response3.getStatusCode());
    }

    @Test
    public void testPutWasteForBid_NoSuchElementException() {

        BidCreateRequest request = new BidCreateRequest();
        when(bidServiceMock.putWasteForBid(request)).thenThrow(NoSuchElementException.class);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<Bid> response = adminController.putWasteForBid(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void testPutWasteForBid_IllegalArgumentException() {
        BidCreateRequest request = new BidCreateRequest();
        when(bidServiceMock.putWasteForBid(request)).thenThrow(IllegalArgumentException.class);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<Bid> response = adminController.putWasteForBid(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testPutWasteForBid_OtherException() {
        BidCreateRequest request = new BidCreateRequest();
        when(bidServiceMock.putWasteForBid(request)).thenThrow(RuntimeException.class);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<Bid> response = adminController.putWasteForBid(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllActiveBids_SuccessfulExecution() {
        List<Bid> activeBids = Collections.singletonList(new Bid());
        when(bidServiceMock.getAllActiveBids()).thenReturn(activeBids);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<List<Bid>> response = adminController.getAllActiveBids();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activeBids, response.getBody());
    }



    @Test
    public void testGetAllActiveBids_IllegalArgumentException() {
        when(bidServiceMock.getAllActiveBids()).thenThrow(IllegalArgumentException.class);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<List<Bid>> response = adminController.getAllActiveBids();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetAllActiveBids_OtherException() {
        when(bidServiceMock.getAllActiveBids()).thenThrow(RuntimeException.class);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<List<Bid>> response = adminController.getAllActiveBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAllBids_SuccessfulExecution() {

        List<Bid> bids = new ArrayList<>();
        when(bidServiceMock.getAllBids()).thenReturn(bids);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<List<Bid>> response = adminController.getAllBids();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bids, response.getBody());
    }

    @Test
    public void testGetAllBids_OtherException() {
        when(bidServiceMock.getAllBids()).thenThrow(RuntimeException.class);

        setAuthenticatedUserWithRole(Role.ADMIN);

        ResponseEntity<List<Bid>> response = adminController.getAllBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    private void setAuthenticatedUserWithRole(Role role) {
        User user = new User();
        user.setRole(role);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}

