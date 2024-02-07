package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.PasswordResetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository
        extends JpaRepository<PasswordResetRequest, String> {
    Optional<PasswordResetRequest> findByRequestId(String requestId);
}
