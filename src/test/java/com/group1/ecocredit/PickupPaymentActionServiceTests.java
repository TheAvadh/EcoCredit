package com.group1.ecocredit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.services.*;
import com.group1.ecocredit.services.implementations.PickupPaymentActionServiceImpl;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class PickupPaymentActionServiceTests {
    @Mock
    private StripeService stripeService;

    @Mock
    private PickupService pickupService;

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private PickupPaymentActionServiceImpl pickupPaymentActionService;

    @Test
    void isPaymentDone_WithNullSessionId_ShouldReturnTrue() throws StripeException {
        assertTrue(pickupPaymentActionService.isPaymentDone(null));
    }

    @Test
    void isPaymentDone_WithValidSessionId_ShouldDelegateToStripeService() throws StripeException {
        String sessionId = "sess_valid";
        when(stripeService.paymentCompleted(sessionId)).thenReturn(true);

        assertTrue(pickupPaymentActionService.isPaymentDone(sessionId));

        verify(stripeService).paymentCompleted(sessionId);
    }

    @Test
    void cancelPayment_WithSessionId_ShouldCallStripeService() throws StripeException {
        Pickup pickup = new Pickup();
        pickup.setPaymentId("sess_cancel");

        pickupPaymentActionService.cancelPayment(pickup);

        verify(stripeService).cancel("sess_cancel");
    }

    @Test
    void cancelPayment_WithoutSessionIdAndWithTransaction_ShouldAddCredit() throws StripeException {
        Pickup pickup = new Pickup();
        Transaction transaction = new Transaction();
        transaction.setUserId(1L);
        transaction.setAmount(new BigDecimal("100.00"));
        when(transactionService.getTransactionFromPickup(pickup)).thenReturn(transaction);

        pickupPaymentActionService.cancelPayment(pickup);

        verify(walletService).addCredit(1L, new BigDecimal("100.00"));
    }

    @Test
    void addSessionIdToPayment_ShouldDelegateToPickupService() {
        Long pickupId = 1L;
        String sessionId = "sess_add";

        pickupPaymentActionService.addSessionIdToPayment(pickupId, sessionId);

        verify(pickupService).addSessionIdToPickup(pickupId, sessionId);
    }

}
