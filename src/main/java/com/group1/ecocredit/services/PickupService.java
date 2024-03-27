package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.PickupStatusResponse;
import com.group1.ecocredit.dto.PickupActionRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.models.admin.PickupQueryResult;
import com.stripe.exception.StripeException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PickupService {
    Pickup schedulePickup(PickupRequest pickupRequest, User user);

    boolean cancelPickup(PickupActionRequest pickupToCancel) throws StripeException;

    List<PickupStatusResponse> getPickupStatus(Long userId);

    void confirmPickup(Long pickupId) throws StripeException;

    void addSessionIdToPickup(Long pickupId, String sessionId);

    public void completePickup(Long pickupId);

    List<Pickup> findByUserId(Long userId);

    List<Pickup> findAllPickupsWithEmailsNotSent();

    List<PickupQueryResult> findScheduledPickups();

    List<PickupQueryResult> findCompletedPickups();

    List<PickupQueryResult> findInProgressPickups();
}
