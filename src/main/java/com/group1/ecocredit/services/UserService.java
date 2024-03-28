package com.group1.ecocredit.services;

import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.ConfirmationToken;
import com.group1.ecocredit.dto.UpdateProfileRequest;
import com.group1.ecocredit.dto.UpdateProfileResponse;
import com.group1.ecocredit.dto.UserDetailsResponse;
import com.group1.ecocredit.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserDetailsService userDetailsService();

    UpdateProfileResponse updateProfile(UpdateProfileRequest updateProfileRequest);

    UserDetailsResponse getUserById(Integer userId);

    User findByRole(Role role);

    Optional<User> findByEmail(String email);

    User save(User user);
}
