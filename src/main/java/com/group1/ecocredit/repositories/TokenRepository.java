package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
