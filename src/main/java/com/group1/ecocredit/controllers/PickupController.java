package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.*;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.services.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import com.group1.ecocredit.dto.PickupCancelRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.dto.PickupStatusResponse;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.JWTService;
import com.group1.ecocredit.services.PickupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pickups")
@CrossOrigin
public class PickupController {

    private final PickupService pickUpService;

    private final JWTService jwtService;

    private final StripeService stripeService;

    private final CheckoutService checkoutService;

    private final WalletService walletService;

    @Value("${base.url.frontend}")
    private String baseUrlFrontend;



    @PostMapping("/cancel")
    @Transactional
    public ResponseEntity<Pickup> pickupCancellation(@RequestBody PickupCancelRequest pickupToCancel) throws StripeException {

        boolean success = pickUpService.cancelPickup(pickupToCancel);

        if(!success) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/schedule")
    @Transactional
    public ResponseEntity<ChargeResponse> schedulePickUp(HttpServletRequest request, @RequestBody PickupRequest pickupRequest) throws StripeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Pickup pickup;
        try {
            pickup = pickUpService.schedulePickup(pickupRequest, user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Long userID = Long.parseLong(jwtService.extractUserID(request.getHeader("Authorization")));
        Long amount = checkoutService.calculatePickupCharge();

        ChargeResponse chargeResponse = new ChargeResponse();

        if(checkoutService.canUserPayByWallet(userID, amount)) {
            walletService.updateCredit(userID, BigDecimal.valueOf(amount), pickup);
            pickUpService.confirmPickup(pickup.getId());
            chargeResponse.setCheckoutUrl(baseUrlFrontend + "/customer/pickup-status");
            return ResponseEntity.ok(chargeResponse);
        }
        String checkoutURL = stripeService.charge(amount, pickup.getId());

        chargeResponse.setCheckoutUrl(checkoutURL);

        return ResponseEntity.ok(chargeResponse);
    }

    @GetMapping("/confirm")
    @Transactional
    public void confirm(@RequestParam(name = "pickupId") Long pickupID) throws StripeException {

        pickUpService.confirmPickup(pickupID);
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<ErrorResponse> handler(StripeException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.ok(errorResponse);
    }

    @GetMapping("/getpickups")
    public ResponseEntity<?> getPickupStatus(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Long userId = Long.parseLong(jwtService.extractUserID(request.getHeader("Authorization")));
        try {
            List<PickupStatusResponse> pickupStatusList = pickUpService.getPickupStatus(userId);

            if (pickupStatusList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(pickupStatusList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

