package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.dto.WasteDto;
import com.group1.ecocredit.models.Category;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.*;
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

    private CategoryRepository categoryRepository;
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

        Pickup savedPickup = pickupRepository.save(pickup);

        for (Waste w : pickupRequest.getWastes()) {
            Waste waste = new Waste();
            Optional<Category> categoryOptional = categoryRepository.findById(w.getCategory_id());
            if (category == null) {
                throw new IllegalArgumentException("Category not found: " + w.getCategory_id());
            }
            Category category = categoryOptional.get();
            waste.setCategory_id(category.getId()); // Set the category ID
            waste.setPickup_id(savedPickup.getId()); // Set the pickup ID
            waste.setWeight(w.getWeight());
            wasteRepository.save(waste);
        }
        System.out.println("Pickup done");


    }
}