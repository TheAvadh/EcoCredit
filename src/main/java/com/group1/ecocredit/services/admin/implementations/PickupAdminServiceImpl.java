package com.group1.ecocredit.services.admin.implementations;

import com.group1.ecocredit.dto.admin.PickupAdminResponse;
import com.group1.ecocredit.dto.admin.WasteAdminResponse;
import com.group1.ecocredit.models.admin.PickupQueryResult;
import com.group1.ecocredit.repositories.admin.PickupAdminRepository;
import com.group1.ecocredit.services.admin.PickupAdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PickupAdminServiceImpl implements PickupAdminService {

    @Autowired
    private PickupAdminRepository pickupAdminRepository;

    @Override
    public List<PickupAdminResponse> getScheduledPickups() throws SQLException {
        var dbPickups = pickupAdminRepository.findScheduledPickups();

        if (dbPickups == null || dbPickups.isEmpty())
            return new ArrayList<>();

        // get distinct pickup ids
        List<Integer> distinctIds = dbPickups.stream()
                .map(PickupQueryResult::getId)
                .distinct()
                .toList();

        List<PickupAdminResponse> pickupResponse = new ArrayList<>();

        for (var id : distinctIds) {
            var pickup = new PickupAdminResponse();
            var dbPickupsWithSameId = dbPickups.stream()
                    .filter(p -> Objects.equals(p.getId(), id)).toList();
            var firstDbPickupOptional =
                    dbPickupsWithSameId.stream().findFirst();

            // get pickup details
            if (firstDbPickupOptional.isPresent()) {
                var firstDbPickup = firstDbPickupOptional.get();
                pickup.setId(firstDbPickup.getId());
                pickup.setUserId(firstDbPickup.getUserId());
                pickup.setDate(firstDbPickup.getDateTime().toLocalDate().toString());
                pickup.setTime(firstDbPickup.getDateTime().toLocalTime().toString());
                pickup.setStatus(firstDbPickup.getStatus());
            }

            // get waste items for the same pickup
            List<WasteAdminResponse> wastes = new ArrayList<>();
            for (var dbPickup : dbPickupsWithSameId) {
                var waste = new WasteAdminResponse();
                waste.setWasteId(dbPickup.getWasteId());
                waste.setWeight(dbPickup.getWeight());
                waste.setCategory(dbPickup.getCategory());
                wastes.add(waste);
            }

            pickup.setWastes(wastes);
            pickupResponse.add(pickup);
        }

        return pickupResponse;
    }
}
