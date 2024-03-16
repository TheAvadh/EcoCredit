package com.group1.ecocredit.controllers;


import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.models.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> putWasteForBid(@RequestBody BidCreateRequest bidCreateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (user.getRole() == Role.USER){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        bidService.putWasteForBid(bidCreateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allactivebids")
    public ResponseEntity<Void> getAllActiveBids(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (user.getRole() == Role.USER){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        bidService.getAllActiveBids();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allbids")
    public ResponseEntity<Void> getAllBids(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (user.getRole() == Role.USER){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        bidService.getAllBids();
        return ResponseEntity.ok().build();
    }

}
