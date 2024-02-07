package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.ForgetPasswordRequest;
import com.group1.ecocredit.models.Role;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.PasswordService;
import com.group1.ecocredit.services.TokenService;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailService emailService;

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
}
