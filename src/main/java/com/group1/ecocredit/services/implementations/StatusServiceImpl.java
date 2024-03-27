package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Status;
import com.group1.ecocredit.repositories.StatusRepository;
import com.group1.ecocredit.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Optional<Status> findByValue(String pickupStatus) {
        return statusRepository.findByValue(pickupStatus);
    }

    @Override
    public Optional<Status> findById(Integer id) {
        return statusRepository.findById(id);
    }

    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }
}
