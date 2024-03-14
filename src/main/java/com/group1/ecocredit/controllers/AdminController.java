package com.group1.ecocredit.controllers;


import com.group1.ecocredit.dto.admin.PickupAdminResponse;
import com.group1.ecocredit.services.admin.PickupAdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@CrossOrigin

public class AdminController {

    private final PickupAdminServiceImpl pickupAdminService;

    @GetMapping("/scheduled-pickups")
    public ResponseEntity<?> getScheduledPickups() {
        List<PickupAdminResponse> pickups;

        try {
            pickups = pickupAdminService.getScheduledPickups();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(pickups);
    }
}
