package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.ForgetPasswordRequest;
import com.group1.ecocredit.dto.PasswordResetRequest;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.TokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.JWTService;
import com.group1.ecocredit.services.PasswordService;
import com.group1.ecocredit.services.TokenService;
import com.group1.ecocredit.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailService emailService;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    Environment env;


    public PasswordServiceImpl(UserRepository userRepository,
                               TokenService tokenService,
                               EmailService emailService,
                               JWTService jwtService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Override
    public boolean forgetPassword(ForgetPasswordRequest request) {
        try {
            var optionalUser = userRepository.findByEmail(request.getEmail());

            if (optionalUser.isEmpty()) {
                throw new IllegalArgumentException("Invalid email %s"
                        .formatted(request.getEmail()));
            }

            var user = optionalUser.get();
            var token = tokenService.generatePasswordResetToken(user.getId());
            tokenService.savePasswordResetRequest(token, user.getId());

            emailService.sendResetPasswordEmail(request.getEmail(), token);
            return true;
        } catch (Exception e) {
            System.out.printf("Failed to send email " +
                            "to [%s] with exception: [%s]%n",
                            request.getEmail(), e.getMessage());
            return false;
        }
    }


    @Override
    public boolean validPasswordResetRequest(String token) {

        String hashedToken = Utils.hash(token);
        PasswordResetToken optionalToken = tokenRepository.findByToken(hashedToken);

        if(optionalToken == null) return false;

        return tokenService.isValidToken(optionalToken);
    }

    @Override
    public boolean resetPassword(String token, PasswordResetRequest request) {

        try {
            if (!Objects.equals(request.getNewPassword(), request.getNewPasswordRepeat())) return false;

            if (validPasswordResetRequest(token)) return false;

            String email = request.getEmail();

            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) return false;

            User userToChangePassword = userOptional.get();

            userToChangePassword.setPassword(request.getNewPassword());

            userRepository.save(userToChangePassword);

            tokenService.inValidateToken(token);

            return true;
        } catch (Exception e) {
            // TODO: Create a logger
            return false;
        }

    }

}
