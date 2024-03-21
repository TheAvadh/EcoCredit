package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.JwtResponseUserModel;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JWTServiceImpl implements JWTService {


    @Value("${EXPIRATION_DURATION_IN_MS}")
    private long expiration_duration_in_ms;

    @Value("${token.secret.key}")
    private String secret_key;

    @Value("${REFRESH_TOKEN_VALIDITY_MS}")
    private long refresh_token_validity_ms;

    @Value("${bearer.size}")
    private Integer bearerSize;

    public String generateToken(User userDetails) {

        Map<String, Object> claimsMap = new HashMap<>();

        JwtResponseUserModel jwtResponseUserModel = getJwtResponseUserModel(userDetails);

        claimsMap.put("user", jwtResponseUserModel);

        return Jwts.builder()
                .setClaims(claimsMap)
                .setSubject(jwtResponseUserModel.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration((new Date(System.currentTimeMillis() + expiration_duration_in_ms)))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static JwtResponseUserModel getJwtResponseUserModel(User userDetails) {
        JwtResponseUserModel jwtResponseUserModel = new JwtResponseUserModel();

        jwtResponseUserModel.setId(userDetails.getId());
        jwtResponseUserModel.setEmail(userDetails.getEmail());
        jwtResponseUserModel.setAddress(userDetails.getAddress());
        jwtResponseUserModel.setRole(userDetails.getRole());
        jwtResponseUserModel.setEnabled(userDetails.isEnabled());
        jwtResponseUserModel.setFirstName(userDetails.getFirstName());
        jwtResponseUserModel.setLastName(userDetails.getLastName());
        jwtResponseUserModel.setPhoneNumber(userDetails.getPhoneNumber());


        return jwtResponseUserModel;
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractUserID(String token) {

        token = token.substring(bearerSize);

        Claims claims = extractAllClaims(token);

        // Extracting the user object from claims
        Object userObject = claims.get("user");

        if (userObject instanceof Map) {
            // Casting userObject to a Map
            Map<?, ?> userMap = (Map<?, ?>) userObject;

            // Extracting the email from the user map
            Object userIDObject = userMap.get("userID");
            if (userIDObject != null) {
                // Casting the email object to String
                return userIDObject.toString();
            }
        }

        return null;
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigninKey() {
        byte[] key = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(key);

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, User userDetails) {

        Map<String, Object> claimsMap = new HashMap<>();

        JwtResponseUserModel jwtResponseUserModel = getJwtResponseUserModel(userDetails);

        claimsMap.put("user", jwtResponseUserModel);

        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refresh_token_validity_ms))
                .setClaims(claimsMap)
                .signWith(getSigninKey(), SignatureAlgorithm.HS256).compact();
    }

}
