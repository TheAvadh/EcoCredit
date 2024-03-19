package com.group1.ecocredit.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@CrossOrigin

public class AdminContoller {

    @GetMapping
    public ResponseEntity<String> sayHello() {

        return ResponseEntity.ok("Hi admin");
    }
}


