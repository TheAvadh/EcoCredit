package com.group1.ecocredit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class SignInRequest {
    private String email;
    private String password;
}
