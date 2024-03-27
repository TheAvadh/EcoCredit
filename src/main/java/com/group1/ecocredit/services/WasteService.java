package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import com.group1.ecocredit.models.Waste;

import java.util.List;
import java.util.Optional;

public interface WasteService {
    boolean updateWeight(Long id, WasteUpdateRequest request);
    Optional<Waste> findById(Long wasteId);

    List<Waste> findByPickupId(Long pickupId);

    Waste save(Waste waste);
}
