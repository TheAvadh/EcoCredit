package com.group1.ecocredit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group1.ecocredit.models.PasswordResetToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
