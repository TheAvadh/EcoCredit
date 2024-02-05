package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.EcoCreditUser;
import com.group1.ecocredit.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<EcoCreditUser, Long> {

    EcoCreditUser findByEmail(String email);

    Password findByPassword_Id(Long id);
}
