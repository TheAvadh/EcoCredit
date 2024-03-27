package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Waste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WasteRepository extends JpaRepository<Waste, Long> {

    Optional<Waste> findById(Long wasteId);

    List<Waste> findByPickupId(Long pickupId);

}
