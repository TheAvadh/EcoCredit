package com.group1.ecocredit;

import com.group1.ecocredit.controllers.PickupController;
import com.group1.ecocredit.dto.*;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.*;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PickupControllerTest {
    private PickupService pickUpService;
    private JWTService jwtService;
    private StripeService stripeService;
    private CheckoutService checkoutService;
    private WalletService walletService;
    private static Authentication authentication;
    private static SecurityContext securityContext;
    private PickupController pickupController;

    private final PickupActionRequest pickupActionRequest =
            new PickupActionRequest(1L);

    @BeforeEach
    void setUp() {
        pickUpService = mock(PickupService.class);
        jwtService = mock(JWTService.class);
        stripeService = mock(StripeService.class);
        checkoutService = mock(CheckoutService.class);
        walletService = mock(WalletService.class);

        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        pickupController = new PickupController(pickUpService, jwtService,
                stripeService, checkoutService, walletService);
    }

    @Test
    public void testPickupCancellation_Success() throws StripeException {
        when(pickUpService.cancelPickup(pickupActionRequest)).thenReturn(true);
        ResponseEntity<Pickup> response = pickupController.pickupCancellation(pickupActionRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pickUpService, times(1)).cancelPickup(pickupActionRequest);
    }

    @Test
    public void testPickupCancellation_Failure() throws StripeException {
        when(pickUpService.cancelPickup(pickupActionRequest)).thenReturn(false);
        ResponseEntity<Pickup> response = pickupController.pickupCancellation(pickupActionRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(pickUpService, times(1)).cancelPickup(pickupActionRequest);
    }

    @Test
    public void testSchedulePickUp_WithValidRequest() throws StripeException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        PickupRequest pickupRequest = new PickupRequest();

        User user = new User();
        user.setId(1);
        authenticate(user);

        Pickup pickup = new Pickup();
        ChargeResponse chargeResponse = new ChargeResponse();
        chargeResponse.setCheckoutUrl("checkout_url");

        when(authentication.getPrincipal()).thenReturn(user);
        when(pickUpService.schedulePickup(pickupRequest, user)).thenReturn(pickup);
        when(checkoutService.calculatePickupCharge()).thenReturn(100L);
        when(jwtService.extractUserID(null)).thenReturn("1");
        when(stripeService.charge(100L, null)).thenReturn("checkout_url");
        ResponseEntity<ChargeResponse> response = pickupController.schedulePickUp(request, pickupRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("checkout_url", response.getBody().getCheckoutUrl());
    }

    @Test
    public void testSchedulePickUp_WithInvalidUser() throws StripeException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        PickupRequest pickupRequest = new PickupRequest();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        authenticate(null);

        ResponseEntity<ChargeResponse> response = pickupController.schedulePickUp(request, pickupRequest);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verifyNoInteractions(pickUpService);
    }

    @Test
    public void testSchedulePickUp_WithInvalidRequest() throws StripeException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        PickupRequest pickupRequest = new PickupRequest();

        User user = new User();
        user.setId(1);
        authenticate(user);

        when(pickUpService.schedulePickup(pickupRequest, user)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<ChargeResponse> response = pickupController.schedulePickUp(request, pickupRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(pickUpService, times(1)).schedulePickup(pickupRequest, user);
    }

    @Test
    public void testSchedulePickUp_WithException() throws StripeException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        PickupRequest pickupRequest = new PickupRequest();

        User user = new User();
        user.setId(1);
        authenticate(user);

        when(pickUpService.schedulePickup(pickupRequest, user)).thenThrow(RuntimeException.class);

        ResponseEntity<ChargeResponse> response = pickupController.schedulePickUp(request, pickupRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(pickUpService, times(1)).schedulePickup(pickupRequest, user);
    }

    @Test
    public void testGetPickupStatus_WithValidRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        List<PickupStatusResponse> pickupStatusList = new ArrayList<>();
        pickupStatusList.add(new PickupStatusResponse(1L, PickupStatus.COMPLETED, "2024-03-27", "00:00"));

        setAuthenticated(true);
        when(jwtService.extractUserID(null)).thenReturn("1");
        when(pickUpService.getPickupStatus(1L)).thenReturn(pickupStatusList);

        ResponseEntity<?> response = pickupController.getPickupStatus(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pickupStatusList, response.getBody());
    }

    @Test
    public void testGetPickupStatus_WithUnauthenticatedRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        setAuthenticated(false);

        ResponseEntity<?> response = pickupController.getPickupStatus(request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verifyNoInteractions(pickUpService);
    }

    @Test
    public void testGetPickupStatus_WithIllegalArgumentException() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        setAuthenticated(true);
        when(jwtService.extractUserID(null)).thenReturn("1");
        when(pickUpService.getPickupStatus(1L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = pickupController.getPickupStatus(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pickUpService, times(1)).getPickupStatus(1L);
    }

    @Test
    public void testGetPickupStatus_WithException() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        setAuthenticated(true);
        when(jwtService.extractUserID(null)).thenReturn("1");
        when(pickUpService.getPickupStatus(1L)).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = pickupController.getPickupStatus(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(pickUpService, times(1)).getPickupStatus(1L);
    }

    @Test
    public void testCompletePickup() {
        PickupActionRequest pickupActionRequest = new PickupActionRequest(1L);
        pickupActionRequest.setId(1L);

        assertDoesNotThrow(() -> pickupController.completePickup(pickupActionRequest));
        verify(pickUpService, times(1)).completePickup(1L);
    }

    private void authenticate(User user){
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    private void setAuthenticated(boolean isAuthenticated){
        when(authentication.isAuthenticated()).thenReturn(isAuthenticated);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
}
