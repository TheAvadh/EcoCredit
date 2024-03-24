package com.group1.ecocredit.services;

import org.springframework.stereotype.Service;

@Service
public interface CheckoutService {

    boolean canUserPayByWallet(Long userId, Long amountInCAD);

    Long calculatePickupCharge();
}
