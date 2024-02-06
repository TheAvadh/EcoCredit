package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.UpdateProfileRequest;
import com.group1.ecocredit.dto.UpdateProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    UpdateProfileResponse updateProfile(UpdateProfileRequest updateProfileRequest);

}
