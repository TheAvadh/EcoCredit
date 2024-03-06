package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.ConfirmationEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfirmationEmailRepository extends JpaRepository<ConfirmationTokenRepository, Long> {

    Optional<List<ConfirmationEmail>> findByEmailSent(boolean EmailSent);

}
