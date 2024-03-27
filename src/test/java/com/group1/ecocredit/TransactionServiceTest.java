package com.group1.ecocredit;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.models.TransactionType;
import com.group1.ecocredit.repositories.TransactionRepository;
import com.group1.ecocredit.services.implementations.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetTransactionFromPickup() {
        Pickup pickup = new Pickup();
        Transaction mockTransaction = new Transaction();
        when(transactionRepository.findByPickup(pickup)).thenReturn(Arrays.asList(mockTransaction));

        Transaction result = transactionService.getTransactionFromPickup(pickup);

        assertEquals(mockTransaction, result);
        verify(transactionRepository, times(1)).findByPickup(pickup);
    }

    @Test
    void testCreateDebitTransaction() {
        Long userId = 1L;
        BigDecimal deductionAmount = BigDecimal.TEN;
        Pickup pickup = new Pickup();
        Transaction savedTransaction = new Transaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.createDebitTransaction(userId, deductionAmount, pickup);

        assertEquals(userId, result.getUserId());
        assertEquals(deductionAmount, result.getAmount());
        assertEquals(TransactionType.DEBIT, result.getTransactionType());
        assertEquals(pickup, result.getPickup());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testGetTransactionsByUserId() {
        Long userId = 1L;
        List<Transaction> mockTransactions = Arrays.asList(new Transaction(), new Transaction());
        when(transactionRepository.findByUserId(userId)).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.getTransactionsByUserId(userId);

        assertEquals(mockTransactions, result);
        verify(transactionRepository, times(1)).findByUserId(userId);
    }
}
