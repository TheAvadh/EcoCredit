package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.UpdateProfileRequest;
import com.group1.ecocredit.dto.UpdateProfileResponse;
import com.group1.ecocredit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")

public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi user");
    }


    @PutMapping("/update-profile/{id}")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        UpdateProfileResponse updateProfileResponse = userService.updateProfile(updateProfileRequest);

        switch (updateProfileResponse.getResponse()) {
            case SUCCESS:
                return ResponseEntity.status(HttpStatus.OK)
                        .body("{\n  \"message\": \"Update profile successful\"\n}");
            case USER_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\n  \"message\": \"User not found\"\n}");
            case INTERNAL_SERVER_ERROR:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\n  \"message\": \"Internal Server Error\"\n}");
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\n  \"message\": \"Unexpected response type\"\n}");
        }
    }
}
