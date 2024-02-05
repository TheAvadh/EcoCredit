package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.EcoCreditUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<EcoCreditUser, Long> {

    EcoCreditUser findByEmail(String email);
}
