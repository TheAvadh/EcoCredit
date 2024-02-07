package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.JwtAuthenticationResponse;
import com.group1.ecocredit.dto.RefreshTokenRequest;
import com.group1.ecocredit.dto.SignInRequest;
import com.group1.ecocredit.dto.SignUpRequest;
import com.group1.ecocredit.models.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
