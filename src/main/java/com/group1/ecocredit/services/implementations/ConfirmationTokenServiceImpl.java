package com.group1.ecocredit.services.implementations;

import com.google.common.hash.Hashing;
import com.group1.ecocredit.models.ConfirmationToken;
import com.group1.ecocredit.repositories.ConfirmationTokenRepository;
import com.group1.ecocredit.services.ConfirmationTokenService;
import com.group1.ecocredit.services.UserService;
import com.group1.ecocredit.services.WalletService;
import com.group1.ecocredit.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Value("${signup.verification.token.hours}")
    private Integer validityInHours;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserService userService;
    private final WalletService walletService;


    @Override
    public String generateConfirmationToken(Integer userId) {
        return java.util.UUID.randomUUID().toString();
    }

    @Override
    public String saveConfirmationToken(String token, User user) {
        var hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();

        var confirmationToken = ConfirmationToken
                .builder()
                .token(hashedToken)
                .createdTime(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusHours(validityInHours))
                .user(user)
                .build();

        confirmationTokenRepository.save(confirmationToken);

        return token;

    }

    @Override
    public boolean confirmToken(String token){
        var hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();

        Optional<ConfirmationToken> confirmationTokenOptional = confirmationTokenRepository.findByToken(hashedToken);

        if(confirmationTokenOptional.isEmpty()) return false;

        ConfirmationToken tokenObj = confirmationTokenOptional.get();

        tokenObj.setUsed(true);

        Optional<User> userOptional = userService.findByEmail(tokenObj.getUser().getEmail());

        if(userOptional.isEmpty()) return false;

        User userToEnable = userOptional.get();

        userToEnable.setEnabled(true);

        confirmationTokenRepository.save(tokenObj);
        userService.save(userToEnable);

        walletService.createWalletForUser(userToEnable);

        return true;
    }

    @Override
    public boolean isValidToken(ConfirmationToken token) {
        return token.getExpirationTime().isAfter(LocalDateTime.now());
    }


}


