package com.group1.ecocredit.repositories;

import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserService extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    User findByRole(Role role);

}
