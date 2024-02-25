package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupRepository extends JpaRepository<Pickup, Long> {
}
