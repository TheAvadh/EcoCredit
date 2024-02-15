package com.group1.ecocredit.services;

import com.group1.ecocredit.models.ConfirmationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserService {
    UserDetailsService userDetailsService();

}
