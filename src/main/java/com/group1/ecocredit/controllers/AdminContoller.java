package com.group1.ecocredit.controllers;


import com.group1.ecocredit.models.Role;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.BidService;
import lombok.RequiredArgsConstructor;
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

    BidService bidService;
    @GetMapping
    public ResponseEntity<String> sayHello(){

        return ResponseEntity.ok("Hi admin");
    }

    @PostMapping("/putwasteforbid")
    public ResponseEntity<Void> putWasteForBid(@RequestParam Long wasteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (user.getRole() == Role.USER){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        bidService.putWasteForBid(wasteId);
        return ResponseEntity.ok().build();
    }

}
