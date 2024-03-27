package com.group1.ecocredit.services;

import com.group1.ecocredit.models.Waste;

import java.util.List;

public interface WasteServiceCustomer {

    List<Waste> getAllWasteForPickup(Long pickupId);
}
