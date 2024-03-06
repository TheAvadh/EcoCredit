package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.ConfirmationEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationEmailRepository extends JpaRepository<ConfirmationEmail, Long> {

}
