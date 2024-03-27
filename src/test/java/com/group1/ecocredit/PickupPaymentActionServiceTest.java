package com.group1.ecocredit;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.services.PickupService;
import com.group1.ecocredit.services.StripeService;
import com.group1.ecocredit.services.TransactionService;
import com.group1.ecocredit.services.WalletService;
import com.group1.ecocredit.services.implementations.PickupPaymentActionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PickupPaymentActionServiceTest {

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCancelPayment_WithPaymentIdNotNull() throws Exception {
        Pickup pickup = new Pickup();
        pickup.setPaymentId("paymentId");

        pickupPaymentActionService.cancelPayment(pickup);

        verify(stripeService, times(1)).cancel("paymentId");
        verifyNoMoreInteractions(walletService);
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    void testCancelPayment_WithPaymentIdNullAndTransactionNotNull() throws Exception {
        Pickup pickup = new Pickup();
        Transaction transaction = new Transaction();
        transaction.setUserId(1L);
        transaction.setAmount(BigDecimal.TEN);

        when(transactionService.getTransactionFromPickup(pickup)).thenReturn(transaction);

        pickupPaymentActionService.cancelPayment(pickup);

        verify(stripeService, never()).cancel(anyString());
        verify(walletService, times(1)).addCredit(1L, BigDecimal.TEN);
        verifyNoMoreInteractions(pickupService);
    }

    @Test
    void testCancelPayment_WithPaymentIdNullAndTransactionNull() throws Exception {
        Pickup pickup = new Pickup();

        when(transactionService.getTransactionFromPickup(pickup)).thenReturn(null);

        pickupPaymentActionService.cancelPayment(pickup);

        verify(stripeService, never()).cancel(anyString());
        verifyNoMoreInteractions(walletService);
        verifyNoMoreInteractions(pickupService);
    }

    @Test
    void testAddSessionIdToPayment() {
        Long pickupId = 1L;
        String sessionId = "sessionId";

        pickupPaymentActionService.addSessionIdToPayment(pickupId, sessionId);

        verify(pickupService, times(1)).addSessionIdToPickup(pickupId, sessionId);
        verifyNoMoreInteractions(stripeService, walletService, transactionService);
    }
}
