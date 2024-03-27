package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.admin.PickupAdminResponse;
import com.group1.ecocredit.dto.admin.WasteAdminResponse;
import com.group1.ecocredit.enums.Currency;
import com.group1.ecocredit.models.Wallet;
import com.group1.ecocredit.models.admin.PickupQueryResult;
import com.group1.ecocredit.repositories.PickupRepository;
import com.group1.ecocredit.services.CheckoutService;
import com.group1.ecocredit.services.CreditConversionService;
import com.group1.ecocredit.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    WalletService walletService;

    @Autowired
    CreditConversionService creditConversionService;

    @Override
    public boolean canUserPayByWallet(Long userId, Long amountInCAD) {
        Optional<Wallet> walletOptional = walletService.getWalletByUserId(userId);

        if(walletOptional.isEmpty()) return false;

        Wallet wallet = walletOptional.get();

        Double credits = creditConversionService.convert(Double.valueOf(amountInCAD), Currency.CAD);

        return wallet.getCreditAmount().doubleValue() > credits;

    }

    @Override
    public Long calculatePickupCharge() {
        Random random = new Random();

        int minPickupValue = 1, maxPickupValue = 10;

        return random.nextLong(maxPickupValue - minPickupValue + 1) + minPickupValue;
    }

    @Service
    @AllArgsConstructor
    public static class PickupAdminServiceImpl implements CreditConversionServiceImpl.PickupAdminService {

        @Autowired
        private PickupRepository pickupRepository;

        @Override
        public List<PickupAdminResponse> getScheduledPickups() throws SQLException {
            var dbPickups = pickupRepository.findScheduledPickups();
            return transform(dbPickups);
        }

        @Override
        public List<PickupAdminResponse> getCompletedPickups() throws SQLException {
            var dbPickups = pickupRepository.findCompletedPickups();
            return transform(dbPickups);
        }

        @Override
        public List<PickupAdminResponse> getInProgressPickups() throws SQLException {
            var dbPickups = pickupRepository.findInProgressPickups();
            return transform(dbPickups);
        }

        private List<PickupAdminResponse> transform(List<PickupQueryResult> dbPickups) {
            if (dbPickups == null || dbPickups.isEmpty())
                return new ArrayList<>();

            List<Long> distinctIds = dbPickups.stream()
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

                if (firstDbPickupOptional.isPresent()) {
                    var firstDbPickup = firstDbPickupOptional.get();
                    pickup.setId(firstDbPickup.getId());
                    pickup.setUserFirstName(firstDbPickup.getUserFirstName());
                    pickup.setUserLastName(firstDbPickup.getUserLastName());
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
}
