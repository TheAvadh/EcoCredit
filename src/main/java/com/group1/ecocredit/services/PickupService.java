package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.PickupCancelRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.User;


public interface PickupService {
    void schedulePickup(PickupRequest pickupRequest, User user);

    boolean cancelPickup(PickupCancelRequest pickupToCancel);
}
