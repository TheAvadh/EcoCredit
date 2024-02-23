package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.PickupRequest;


public interface PickupService {
    void schedulePickup(PickupRequest pickupRequest);
}
