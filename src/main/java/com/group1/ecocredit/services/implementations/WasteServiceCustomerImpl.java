package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.WasteServiceCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WasteServiceCustomerImpl implements WasteServiceCustomer {

    @Autowired
    private WasteRepository wasteRepository;

    @Override
    public List<Waste> getAllWasteForPickup(Long pickupId) {

        return wasteRepository.findByPickupId(pickupId);
    }
}
