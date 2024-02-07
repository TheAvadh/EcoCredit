package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.ForgetPasswordRequest;
import com.group1.ecocredit.models.PasswordReset;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.TokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.PasswordService;
import com.group1.ecocredit.services.TokenService;
import com.group1.ecocredit.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
                               EmailService emailService) {
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
    public PasswordResetToken getPasswordResetToken(User user) {

        Long validityHours = Long.parseLong(Objects.requireNonNull(env.getProperty("password.reset.validity.hours")));

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setExpirationTime(LocalDateTime.now().plusHours(validityHours));
        resetToken.setToken(UUID.randomUUID().toString());

        // TODO: use passwordResetToken object to generate a jwt token

        // TODO: store url link to DB table - passwordresettoken
        tokenRepository.save(resetToken);

        return resetToken;
    }

    @Override
    public boolean isValidToken(PasswordResetToken token) {
        return token.getExpirationTime().isAfter(LocalDateTime.now());
    }

    @Override
    public void inValidateToken(String token) {

        PasswordResetToken tokenToInvalidate = tokenRepository.findByToken(token);

        tokenToInvalidate.setUsed(true);

        tokenRepository.save(tokenToInvalidate);
    }

    @Override
    public boolean validPasswordResetRequest(String token) {

        String hashedToken = Utils.hash(token);
        PasswordResetToken optionalToken = tokenRepository.findByToken(hashedToken);

        if(optionalToken == null) return false;

        return isValidToken(optionalToken);
    }

    @Override
    public boolean resetPassword(String token, PasswordReset request) {

        try {
            if (!Objects.equals(request.getNewPassword(), request.getNewPasswordRepeat())) return false;

            if (validPasswordResetRequest(token)) return false;

            String email = request.getEmail();

            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) return false;

            User userToChangePassword = userOptional.get();

            userToChangePassword.setPassword(request.getNewPassword());

            userRepository.save(userToChangePassword);

            inValidateToken(token);

            return true;
        } catch (Exception e) {
            // TODO: Create a logger
            return false;
        }

    }

}
