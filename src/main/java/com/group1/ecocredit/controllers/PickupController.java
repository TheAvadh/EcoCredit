package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.PickupCancelRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.services.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pickups")
@CrossOrigin
public class PickupController {

    private final PickupService pickUpService;

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
        pickUpService.schedulePickup(request);
        return ResponseEntity.ok("Pickup scheduled successfully.");
    }
}
