package com.group1.ecocredit;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.models.TransactionType;
import com.group1.ecocredit.repositories.TransactionRepository;
import com.group1.ecocredit.services.implementations.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Pickup mockPickup;
    private Transaction mockTransaction;

    @BeforeEach
    void setUp() {
        mockPickup = new Pickup(); // Assuming Pickup has a no-args constructor
        // Setup mockTransaction as needed
        mockTransaction = new Transaction();
        mockTransaction.setUserId(1L);
        mockTransaction.setAmount(new BigDecimal("100.00"));
        mockTransaction.setTransactionType(TransactionType.DEBIT);
        mockTransaction.setTimestamp(LocalDateTime.now());
        mockTransaction.setPickup(mockPickup);
    }

    @Test
    void getTransactionFromPickup_ShouldReturnFirstTransaction() {
        LinkedList<Transaction> transactions = new LinkedList<>();
        transactions.add(mockTransaction);
        when(transactionRepository.findByPickup(mockPickup)).thenReturn(transactions);

        Transaction result = transactionService.getTransactionFromPickup(mockPickup);

        assertEquals(mockTransaction, result);
        verify(transactionRepository).findByPickup(mockPickup);
    }

    @Test
    void createDebitTransaction_ShouldSaveTransaction() {
        BigDecimal deductionAmount = new BigDecimal("50.00");
        Long userId = 2L;

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.createDebitTransaction(userId, deductionAmount, mockPickup);

        assertNotNull(result);
        assertEquals(TransactionType.DEBIT, result.getTransactionType());
        assertEquals(deductionAmount, result.getAmount());
        assertEquals(userId, result.getUserId());
        assertNotNull(result.getTimestamp());
        assertEquals(mockPickup, result.getPickup());
    }

    @Test
    void getTransactionsByUserId_ShouldReturnListOfTransactions() {
        List<Transaction> expectedTransactions = Arrays.asList(mockTransaction);
        when(transactionRepository.findByUserId(1L)).thenReturn(expectedTransactions);

        List<Transaction> result = transactionService.getTransactionsByUserId(1L);

        assertEquals(expectedTransactions, result);
        verify(transactionRepository).findByUserId(1L);
    }



}
