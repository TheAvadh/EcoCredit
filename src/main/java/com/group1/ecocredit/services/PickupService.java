package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.PickupCancelRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.models.Pickup;


public interface PickupService {
    void schedulePickup(PickupRequest pickupRequest);

    boolean cancelPickup(PickupCancelRequest pickupToCancel);
}
