package com.group1.ecocredit.controllers;


import com.group1.ecocredit.services.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/putWasteForBid")
    public ResponseEntity<Void> putWasteForBid(@RequestParam Long wasteId) {
        bidService.putWasteForBid(wasteId);
        return ResponseEntity.ok().build();
    }

}
