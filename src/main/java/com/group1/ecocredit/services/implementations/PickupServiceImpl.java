package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.PickupCancelRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.dto.PickupStatusResponse;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.*;
import com.group1.ecocredit.services.PickupPaymentActionService;
import com.group1.ecocredit.services.PickupService;
import com.group1.ecocredit.services.PriceMapperService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@CrossOrigin
public class PickupServiceImpl implements PickupService {

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private WasteRepository wasteRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private PickupPaymentActionService pickupPaymentActionService;

    @Autowired
    private TransactionRepository transactionRepository;



    @Override
    public Pickup schedulePickup(PickupRequest pickupRequest, User user) {
        Optional<Status> statusOptional =
                statusRepository.findByValue(PickupStatus.AWAITING_PAYMENT);
        if (statusOptional.isEmpty()) {
            throw new IllegalArgumentException("Status not found: " + PickupStatus.AWAITING_PAYMENT);
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
        System.out.println("Pickup scheduled");

        return savedPickup;
    }
    @Override
    public boolean cancelPickup(PickupCancelRequest pickupToCancel) throws StripeException {

        Optional<Pickup> pickupOptional = pickupRepository.findById(pickupToCancel.getId());
        if(pickupOptional.isEmpty()) return false;


        Optional<Status> statusCanceledOptional =
                statusRepository.findByValue(PickupStatus.CANCELED);
        if(statusCanceledOptional.isEmpty()) return false;


        Pickup pickup = pickupOptional.get();

        if(Objects.equals(pickup.getStatus().getValue(), PickupStatus.COMPLETED)) return false;

        Status canceled = statusCanceledOptional.get();

        pickupPaymentActionService.cancelPayment(pickup);

        pickup.setStatus(canceled);
        pickupRepository.save(pickup);
        return true;
    }


    @Override
    public List<PickupStatusResponse> getPickupStatus(Long userId) {
        List<PickupStatusResponse> pickupStatusList = new ArrayList<>();

        List<Pickup> pickups = pickupRepository.findByUserId(userId);

        if (pickups.isEmpty()) {
            throw new IllegalArgumentException("No pickups found for the user");
        }

        for (Pickup pickup : pickups) {
            LocalDateTime pickupDateTime = pickup.getDateTime();
            String pickupDate = pickupDateTime.toLocalDate().toString();
            String pickupTime = pickupDateTime.toLocalTime().toString();
            String pickupStatus = pickup.getStatus().getValue();
            PickupStatusResponse response = new PickupStatusResponse(pickup.getId(), pickupStatus, pickupDate, pickupTime);
            pickupStatusList.add(response);
        }

        return pickupStatusList;
    }

    @Override
    public void confirmPickup(Long pickupId) throws StripeException {
        Optional<Pickup> optionalPickup = pickupRepository.findById(pickupId);

        if(optionalPickup.isEmpty()) return;

        Pickup pickup = optionalPickup.get();

        if(pickupPaymentActionService.isPaymentDone(pickup.getPaymentId())) {

            Status status = statusRepository.findByValue(PickupStatus.SCHEDULED).get();
            pickup.setStatus(status);
            pickupRepository.save(pickup);
        }

    }

    @Override
    public void addSessionIdToPickup(Long pickupId, String sessionId) {

        Optional<Pickup> pickupOptional = pickupRepository.findById(pickupId);

        if(pickupOptional.isEmpty()) return;

        Pickup pickup = pickupOptional.get();

        pickup.setPaymentId(sessionId);

        pickupRepository.save(pickup);
    }
}
