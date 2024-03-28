package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.ForgetPasswordRequest;
import com.group1.ecocredit.dto.PasswordResetRequest;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.PasswordResetTokenRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.PasswordService;
import com.group1.ecocredit.services.TokenService;
import com.group1.ecocredit.services.UserService;
import com.group1.ecocredit.utils.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    public PasswordServiceImpl(UserService userService,
                               TokenService tokenService,
                               EmailService emailService,
                               PasswordResetTokenRepository passwordResetTokenRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean forgetPassword(ForgetPasswordRequest request) {

        try {
            var optionalUser = userService.findByEmail(request.getEmail());

            if (optionalUser.isEmpty()) {
                throw new IllegalArgumentException("Invalid email %s"
                        .formatted(request.getEmail()));
            }

            var user = optionalUser.get();
            var token = tokenService.generatePasswordResetToken(user.getId());
            tokenService.savePasswordResetToken(token, user);

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
        PasswordResetToken optionalToken = passwordResetTokenRepository.findByToken(hashedToken);

        if(optionalToken == null) return false;

        return tokenService.isValidToken(optionalToken);
    }

    @Override
    public boolean resetPassword(String token, PasswordResetRequest request) {

        try {
            if (!Objects.equals(request.getNewPassword(), request.getNewPasswordRepeat())) return false;


            if (!validPasswordResetRequest(token)) return false;

            String email = request.getEmail();

            Optional<User> userOptional = userService.findByEmail(email);

            if (userOptional.isEmpty()) return false;

            User userToChangePassword = userOptional.get();

            userToChangePassword.setPassword(passwordEncoder.encode(request.getNewPassword()));

            userService.save(userToChangePassword);

            tokenService.inValidateToken(token);

            return true;
        } catch (Exception e) {
            System.out.println("Failed to reset password" );

            return false;
        }

    }

}
