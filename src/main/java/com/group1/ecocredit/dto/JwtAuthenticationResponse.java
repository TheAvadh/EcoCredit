package com.group1.ecocredit.dto;


import com.group1.ecocredit.enums.HttpMessage;
import com.group1.ecocredit.models.Role;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private HttpMessage httpMessage;
    private Role role;
}
