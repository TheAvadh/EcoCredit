package com.group1.ecocredit.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class RefreshTokenRequest {
    private String token;

}
