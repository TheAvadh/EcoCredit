package com.group1.ecocredit.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")

public class AdminContoller {
    @GetMapping
    public ResponseEntity<String> sayHello(){

        return ResponseEntity.ok("Hi admin");
    }
}