package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.DisplayBidRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.AuctionService;
import com.group1.ecocredit.services.implementations.AuctionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recycler")
public class RecyclerController {
    @Autowired
    AuctionService auctionService;

    @GetMapping("/activebids")
    public ResponseEntity<?> getAllActiveBids() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try{
            List<Bid> bidList = auctionService.viewAllActiveBids();
            if (bidList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(bidList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/viewmybids/{userId}")
    public ResponseEntity<?> getUserBids(@PathVariable Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            List<Bid> bidList = auctionService.viewUserBids(userId);
            if (bidList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(bidList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/placeOrUpdate")
    public ResponseEntity<Bid> placeOrUpdateBid(@RequestParam DisplayBidRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            Bid bid = auctionService.placeOrUpdateBid(request, user);
            return ResponseEntity.ok(bid);
        } catch (AuctionServiceImpl.BidNotFoundException | AuctionServiceImpl.InvalidBidAmountException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /*@PostMapping("/increment/{bidId}")
    public ResponseEntity<Bid> incrementBid(@PathVariable Long bidId) {
        try {
            Bid bid = auctionService.incrementBid(bidId);
            return ResponseEntity.ok(bid);
        } catch (AuctionServiceImpl.BidNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }*/
}
