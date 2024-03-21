package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRepository extends JpaRepository<Waste, Long> {

    Waste findByPickupId(Long pickupId);

}
