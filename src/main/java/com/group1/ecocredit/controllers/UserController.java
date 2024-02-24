package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.UpdateProfileRequest;
import com.group1.ecocredit.dto.UpdateProfileResponse;
import com.group1.ecocredit.dto.UserDetailsResponse;
import com.group1.ecocredit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi user");
    }


    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody UpdateProfileRequest updateProfileRequest) {
        UpdateProfileResponse updateProfileResponse = userService.updateProfile(updateProfileRequest);

        return switch (updateProfileResponse.getResponse()) {
            case SUCCESS -> ResponseEntity.status(HttpStatus.OK)
                    .body("{\n  \"message\": \"Update profile successful\"\n}");
            case USER_NOT_FOUND ->
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("{\n  \"message\": \"User not found\"\n}");
            case INTERNAL_SERVER_ERROR ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\n  \"message\": \"Internal Server Error\"\n}");
            default -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\n  \"message\": \"Unexpected response type\"\n}");
        };
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
        UserDetailsResponse userDTO = userService.getUserById(userId);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
