package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status,Integer> {
    Optional<Status> findByValue(String pickupStatus);
}
