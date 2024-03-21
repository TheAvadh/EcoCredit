package com.group1.ecocredit.controllers;


import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@CrossOrigin

public class AdminContoller {

    private final BidService bidService;
    @GetMapping
    public ResponseEntity<String> sayHello(){

        return ResponseEntity.ok("Hi admin");
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
