package com.group1.ecocredit.services.implementations;

import com.google.common.hash.Hashing;
import com.group1.ecocredit.models.ConfirmationToken;
import com.group1.ecocredit.repositories.ConfirmationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import com.group1.ecocredit.models.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor

public class ConfirmationTokenService {

/*    @Value("${signup.verification.token.hours}")
    private Integer validityInHours;*/


    private final ConfirmationTokenRepository confirmationTokenRepository;


    public String generateConfirmationToken(Integer userId) {
        return java.util.UUID.randomUUID().toString();
    }

    public String saveConfirmationToken(String token, User user) {
        var hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();

        var confirmationToken = ConfirmationToken
                .builder()
                .token(hashedToken)
                .createdTime(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusHours(5000))
                .user(user)
                .build();

        confirmationTokenRepository.save(confirmationToken);

        return token;

    }

    public boolean isValidToken(ConfirmationToken token) {
        return token.getExpirationTime().isAfter(LocalDateTime.now());
    }
/*
    @Transactional
    public Optional<ConfirmationToken> confirmToken(String token) throws IllegalStateException{

        confirmationTokenRepository.findByToken(token).orElseThrow(()-> new IllegalStateException("token not found"));
        if (confirmationToken.isVerified() != false){
            throw new IllegalStateException("email is already confirmed");
        }




    }*/

}


