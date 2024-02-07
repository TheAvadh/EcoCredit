package com.group1.ecocredit.dto;


import com.group1.ecocredit.models.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private Role role;
}
