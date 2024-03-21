package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.services.*;
import com.stripe.exception.StripeException;
import org.hibernate.resource.transaction.backend.jta.internal.JtaTransactionAdapterTransactionManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

// This class is so that we can remove circular dependency between stripeService and pickupService
@Service
public class PickupPaymentActionServiceImpl implements PickupPaymentActionService {

    @Autowired
    @Lazy
    private StripeService stripeService;

    @Autowired
    @Lazy
    private PickupService pickupService;

    @Autowired
    @Lazy
    private WalletService walletService;

    @Autowired
    @Lazy
    private TransactionService transactionService;

    @Override
    public boolean isPaymentDone(String sessionId) throws StripeException {

        if(sessionId == null) return true;

        return stripeService.paymentCompleted(sessionId);
    }

    @Override
    public void cancelPayment(Pickup pickup) throws StripeException {

        String sessionId = pickup.getPaymentId();

        if(sessionId != null) {
            stripeService.cancel(sessionId);
        } else {
            Transaction transaction = transactionService.getTransactionFromPickup(pickup);

            if(transaction == null) {
                return;
            }

            walletService.addCredit(transaction.getUserId(), transaction.getAmount());
        }
    }

    @Override
    public void addSessionIdToPayment(Long pickupId, String sessionId) {

        pickupService.addSessionIdToPickup(pickupId, sessionId);
    }


}
