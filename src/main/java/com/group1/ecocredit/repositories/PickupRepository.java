package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickupRepository extends JpaRepository<Pickup, Long> {
    List<Pickup> findByUserId(Long userId);
}
