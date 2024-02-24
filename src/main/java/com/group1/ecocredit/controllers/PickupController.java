package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.services.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pickup")
public class PickupController {
    @Autowired
    private PickupService pickUpService;

    @PostMapping("/schedule")
    public ResponseEntity<String> schedulePickUp(@RequestBody PickupRequest request) {
        pickUpService.schedulePickup(request);
        return ResponseEntity.ok("Pickup scheduled successfully.");
    }
}
