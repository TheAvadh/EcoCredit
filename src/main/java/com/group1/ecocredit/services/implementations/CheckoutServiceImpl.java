package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.enums.Currency;
import com.group1.ecocredit.models.Wallet;
import com.group1.ecocredit.services.CheckoutService;
import com.group1.ecocredit.services.CreditConversionService;
import com.group1.ecocredit.services.JWTService;
import com.group1.ecocredit.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
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

        Double amountInCredits = creditConversionService.convert(Double.valueOf(amountInCAD), Currency.CAD);

        return wallet.getCreditAmount().doubleValue() >= amountInCredits;

    }

    @Override
    public Long calculatePickupCharge() {
        Random random = new Random();

        int minPickupValue = 1, maxPickupValue = 10;

        return random.nextLong(maxPickupValue - minPickupValue + 1) + minPickupValue;
    }
}
