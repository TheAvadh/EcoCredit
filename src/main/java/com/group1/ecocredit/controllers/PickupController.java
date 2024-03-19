package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.PickupCancelRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.dto.PickupStatusResponse;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.JWTService;
import com.group1.ecocredit.services.PickupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pickups")
@CrossOrigin
public class PickupController {

    private final PickupService pickUpService;

    private final JWTService jwtService;

    @Value("${bearer.size}")
    private Integer bearerSize;

    @PostMapping("/cancel")
    public ResponseEntity<Pickup> pickupCancellation(@RequestBody PickupCancelRequest pickupToCancel) {

        boolean success = pickUpService.cancelPickup(pickupToCancel);

        if(!success) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
    @PostMapping("/schedule")
    public ResponseEntity<String> schedulePickUp(@RequestBody PickupRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            pickUpService.schedulePickup(request, user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok("Pickup scheduled successfully.");
    }

    @GetMapping("getpickups")
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

