package com.group1.ecocredit.controllers;


import com.group1.ecocredit.models.Bid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@CrossOrigin

public class AdminContoller {
    @GetMapping
    public ResponseEntity<String> sayHello(){

        return ResponseEntity.ok("Hi admin");
    }

    @PostMapping("/put_for_bidding")
    public ResponseEntity<String> putForBidding(@RequestBody Bid bid){
        return ResponseEntity.ok("The waste is added to auction successfully");
    }
}
