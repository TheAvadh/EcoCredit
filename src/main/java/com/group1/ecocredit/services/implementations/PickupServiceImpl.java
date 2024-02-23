package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.dto.WasteDto;
import com.group1.ecocredit.models.Category;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.PickupRepository;
import com.group1.ecocredit.repositories.StatusRepository;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.PickupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PickupServiceImpl implements PickupService {

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private WasteRepository wasteRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;


    private Category category;

    @Override
    public void schedulePickup(PickupRequest pickupRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();

        Pickup pickup = new Pickup();
        pickup.setDateTime(pickupRequest.getDateTime());
        pickup.setStatus_id(category.getId());
        pickup.setUser_id(userId); // Set the user

        pickupRepository.save(pickup);
        System.out.println("Pickup done");



    }
}
