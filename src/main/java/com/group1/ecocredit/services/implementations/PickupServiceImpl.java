package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.PickupCancelRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.*;
import com.group1.ecocredit.services.PickupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void schedulePickup(PickupRequest pickupRequest) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            Integer userId = user.getId();

            Optional<Status> statusOptional =
                    statusRepository.findByStatus(PickupStatus.SCHEDULED);
            if (statusOptional.isEmpty()) {
                throw new IllegalArgumentException("Status not found: " + PickupStatus.SCHEDULED);
            }

            Pickup pickup = new Pickup();
            pickup.setDateTime(LocalDateTime.parse(pickupRequest.getDateTime()));
            pickup.setStatus(statusOptional.get());
            pickup.setUser(user);

            Pickup savedPickup = pickupRepository.save(pickup);

            for (var w : pickupRequest.getWastes()) {
                Waste waste = new Waste();
                Optional<Category> categoryOptional =
                        categoryRepository.findByValue(w.getCategory());
                if (categoryOptional.isEmpty()) {
                    throw new IllegalArgumentException("Category not found: " + w.getCategory());
                }
                Category category = categoryOptional.get();
                waste.setCategory(category);
                waste.setPickup(savedPickup);
                waste.setWeight(w.getWeight());
                wasteRepository.save(waste);
            }
            System.out.println("Pickup done");
        } catch (Exception e) {
            System.out.printf("Caught exception while scheduling pickup: %s%n",
                    e.getMessage());
        }
    }
    @Override
    public boolean cancelPickup(PickupCancelRequest pickupToCancel) {

        Optional<Pickup> pickupOptional = pickupRepository.findById(pickupToCancel.getId());
        Optional<Status> statusOptional =
                statusRepository.findByStatus(PickupStatus.CANCELED);

        if(pickupOptional.isEmpty()) return false;
        if(statusOptional.isEmpty()) return false;

        Pickup pickup = pickupOptional.get();
        Status status = statusOptional.get();
        pickup.setStatus(status);
        pickupRepository.save(pickup);
        return true;
    }
}
