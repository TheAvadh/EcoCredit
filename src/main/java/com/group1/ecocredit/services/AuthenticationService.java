package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.JwtAuthenticationResponse;
import com.group1.ecocredit.dto.SignInRequest;
import com.group1.ecocredit.dto.SignUpRequest;
import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest signUpRequest) throws MessagingException;

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    interface WasteService {
        boolean updateWeight(Long id, WasteUpdateRequest request);
    }
}
