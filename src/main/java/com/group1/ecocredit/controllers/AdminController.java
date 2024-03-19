package com.group1.ecocredit.controllers;


import com.group1.ecocredit.dto.admin.PickupAdminResponse;
import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import com.group1.ecocredit.services.admin.implementations.PickupAdminServiceImpl;
import com.group1.ecocredit.services.admin.implementations.WasteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@CrossOrigin

public class AdminController {

    private final PickupAdminServiceImpl pickupAdminService;
    private final WasteServiceImpl wasteService;

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

    @PatchMapping("/wastes/{wasteId}")
    public ResponseEntity<?> updateWeight(@PathVariable Long wasteId,
                                          @RequestBody WasteUpdateRequest request) {
        try {
            var result = wasteService.updateWeight(wasteId, request);
            if (!result) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }
}
