package com.group1.ecocredit.controllers;


import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.dto.admin.PickupAdminResponse;
import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.BidService;
import com.group1.ecocredit.services.PickupAdminService;
import com.group1.ecocredit.services.WasteService;
import com.group1.ecocredit.services.implementations.PickupAdminServiceImpl;
import com.group1.ecocredit.services.implementations.WasteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@CrossOrigin

public class AdminController {

    private final PickupAdminService pickupAdminService;
    private final WasteService wasteService;
    private final BidService bidService;

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

    @GetMapping("/completed-pickups")
    public ResponseEntity<?> getCompletedPickups() {
        List<PickupAdminResponse> pickups;

        try {
            pickups = pickupAdminService.getCompletedPickups();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(pickups);
    }

    @GetMapping("/in-progress-pickups")
    public ResponseEntity<?> getInProgressPickups() {
        List<PickupAdminResponse> pickups;

        try {
            pickups = pickupAdminService.getInProgressPickups();
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

    @PostMapping("/putwasteforbid")
    public ResponseEntity<Bid> putWasteForBid(@RequestBody BidCreateRequest bidCreateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (user.getRole() == Role.USER){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try{
            Bid bid = bidService.putWasteForBid(bidCreateRequest);
            return ResponseEntity.ok(bid);
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/allactivebids")
    public ResponseEntity<List<Bid>> getAllActiveBids(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (user.getRole() == Role.USER){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try{
            List<Bid> bidList = bidService.getAllActiveBids();
            return ResponseEntity.ok(bidList);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/allbids")
    public ResponseEntity<List<Bid>> getAllBids(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (user.getRole() == Role.USER){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try{
            List<Bid> bidList = bidService.getAllBids();
            return ResponseEntity.ok(bidList);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
