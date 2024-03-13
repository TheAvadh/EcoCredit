package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WasteRepository extends JpaRepository<Waste, Long> {

    Optional<Waste> findById(Long wasteId);
}
