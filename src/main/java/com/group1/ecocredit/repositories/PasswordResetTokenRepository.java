package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
