package com.group1.ecocredit;

import com.group1.ecocredit.controllers.AdminController;
import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.dto.admin.PickupAdminResponse;
import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminControllerTests {
    private BidService bidServiceMock;

    private PickupAdminService pickupAdminServiceMock;
    private WasteService wasteServiceMock;

    private AdminController adminController;
    private static Authentication authentication;
    private static SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        bidServiceMock = mock(BidService.class);
        pickupAdminServiceMock = mock(PickupAdminService.class);
        wasteServiceMock = mock(WasteService.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        adminController = new AdminController(pickupAdminServiceMock,
                wasteServiceMock, bidServiceMock);
    }

    @Test
    public void testPutWasteForBid_SuccessfulExecution() {

        BidCreateRequest request = new BidCreateRequest();
        Bid expectedBid = new Bid();
        when(bidServiceMock.putWasteForBid(request)).thenReturn(expectedBid);

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<Bid> response = adminController.putWasteForBid(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBid, response.getBody());
    }

    @Test
    public void testPutWasteForBid_UnauthorizedUser() {

        BidCreateRequest request = new BidCreateRequest();

        User user = new User();
        user.setRole(Role.USER);
        authenticate(user);

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

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<Bid> response = adminController.putWasteForBid(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void testPutWasteForBid_IllegalArgumentException() {
        BidCreateRequest request = new BidCreateRequest();
        when(bidServiceMock.putWasteForBid(request)).thenThrow(IllegalArgumentException.class);

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<Bid> response = adminController.putWasteForBid(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testPutWasteForBid_OtherException() {
        BidCreateRequest request = new BidCreateRequest();
        when(bidServiceMock.putWasteForBid(request)).thenThrow(RuntimeException.class);

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<Bid> response = adminController.putWasteForBid(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllActiveBids_SuccessfulExecution() {
        List<Bid> activeBids = Collections.singletonList(new Bid());
        when(bidServiceMock.getAllActiveBids()).thenReturn(activeBids);

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<List<Bid>> response = adminController.getAllActiveBids();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activeBids, response.getBody());
    }



    @Test
    public void testGetAllActiveBids_IllegalArgumentException() {
        when(bidServiceMock.getAllActiveBids()).thenThrow(IllegalArgumentException.class);

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<List<Bid>> response = adminController.getAllActiveBids();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetAllActiveBids_OtherException() {
        when(bidServiceMock.getAllActiveBids()).thenThrow(RuntimeException.class);

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<List<Bid>> response = adminController.getAllActiveBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAllBids_SuccessfulExecution() {

        List<Bid> bids = new ArrayList<>();
        when(bidServiceMock.getAllBids()).thenReturn(bids);

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<List<Bid>> response = adminController.getAllBids();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bids, response.getBody());
    }

    @Test
    public void testGetAllBids_OtherException() {
        when(bidServiceMock.getAllBids()).thenThrow(RuntimeException.class);

        User user = new User();
        user.setRole(Role.ADMIN);
        authenticate(user);

        ResponseEntity<List<Bid>> response = adminController.getAllBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetScheduledPickups_SuccessfulExecution() throws SQLException {

        List<PickupAdminResponse> pickups = new ArrayList<>();
        when(pickupAdminServiceMock.getScheduledPickups()).thenReturn(pickups);

        ResponseEntity<?> response = adminController.getScheduledPickups();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pickups, response.getBody());
    }

    @Test
    public void testGetScheduledPickups_Exception() throws SQLException {

        when(pickupAdminServiceMock.getScheduledPickups()).thenThrow(SQLException.class);

        ResponseEntity<?> response = adminController.getScheduledPickups();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetCompletedPickups_SuccessfulExecution() throws SQLException {

        List<PickupAdminResponse> pickups = new ArrayList<>();
        when(pickupAdminServiceMock.getCompletedPickups()).thenReturn(pickups);

        ResponseEntity<?> response = adminController.getCompletedPickups();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pickups, response.getBody());
    }

    @Test
    public void testGetCompletedPickups_Exception() throws SQLException {

        when(pickupAdminServiceMock.getCompletedPickups()).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = adminController.getCompletedPickups();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }




    @Test
    public void testGetInProgressPickups_SuccessfulExecution() throws SQLException {

        List<PickupAdminResponse> pickups = new ArrayList<>();
        when(pickupAdminServiceMock.getCompletedPickups()).thenReturn(pickups);

        ResponseEntity<?> response = adminController.getInProgressPickups();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pickups, response.getBody());
    }

    @Test
    public void testGetInProgressPickups_Exception() throws SQLException {
        when(pickupAdminServiceMock.getInProgressPickups()).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = adminController.getInProgressPickups();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateWeight_SuccessfulExecution() {
        when(wasteServiceMock.updateWeight(anyLong(), any(WasteUpdateRequest.class))).thenReturn(true);

        ResponseEntity<?> response = adminController.updateWeight(1L, new WasteUpdateRequest());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateWeight_Failure() {
        when(wasteServiceMock.updateWeight(anyLong(), any(WasteUpdateRequest.class))).thenReturn(false);

        ResponseEntity<?> response = adminController.updateWeight(1L, new WasteUpdateRequest());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private void authenticate(User user){
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
}

