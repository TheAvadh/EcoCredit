package com.group1.ecocredit.services;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Transaction;
import com.stripe.exception.StripeException;

import java.math.BigDecimal;

public interface PickupPaymentActionService {
        boolean isPaymentDone(String paymentId) throws StripeException;

        void cancelPayment(Pickup pickup) throws StripeException;

        void addSessionIdToPayment(Long pickupId, String sessionId);
}
