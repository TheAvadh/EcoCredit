package com.group1.ecocredit.services;

import com.group1.ecocredit.models.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String extractUserName(String token);

    String extractUserID(String token);

    String generateToken(User userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, User userDetails);

}
