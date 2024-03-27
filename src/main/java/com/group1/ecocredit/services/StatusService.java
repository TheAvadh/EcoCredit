package com.group1.ecocredit.services;

import com.group1.ecocredit.models.Status;

import java.util.Optional;

public interface StatusService {
    Optional<Status> findByValue(String pickupStatus);

    Optional<Status> findById(Integer id);

    Status save(Status status);
}
