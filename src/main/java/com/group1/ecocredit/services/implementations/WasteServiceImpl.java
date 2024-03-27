package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteServiceImpl implements WasteService {
    @Autowired
    private WasteRepository wasteRepository;

    @Override
    public boolean updateWeight(Long id, WasteUpdateRequest request) {
        var optionalWaste = wasteRepository.findById(id);
        if (optionalWaste.isEmpty()) {
            return false;
        }
        var waste = optionalWaste.get();
        waste.setWeight(request.getWeight());
        wasteRepository.save(waste);
        return true;
    }

    @Override
    public Optional<Waste> findById(Long wasteId) {
        return wasteRepository.findById(wasteId);
    }

    @Override
    public List<Waste> findByPickupId(Long pickupId) {
        return wasteRepository.findByPickupId(pickupId);
    }

    @Override
    public Waste save(Waste waste) {
        return wasteRepository.save(waste);
    }
}
