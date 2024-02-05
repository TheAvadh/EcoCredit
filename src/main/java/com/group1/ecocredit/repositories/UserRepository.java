package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Role;
import com.group1.ecocredit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    User findByRole(Role role);

}
