package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.ConfirmationEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationEmailRepository extends JpaRepository<ConfirmationEmail, Long> {

}
