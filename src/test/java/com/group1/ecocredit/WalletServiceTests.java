package com.group1.ecocredit;

import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.models.TransactionType;
import com.group1.ecocredit.models.Wallet;
import com.group1.ecocredit.repositories.TransactionRepository;
import com.group1.ecocredit.repositories.WalletRepository;
import com.group1.ecocredit.services.TransactionService;
import com.group1.ecocredit.services.implementations.TransactionServiceImpl;
import com.group1.ecocredit.services.implementations.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTests {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private WalletServiceImpl walletService;

    private TransactionService transactionService;

    private Wallet wallet;
    private Long userId;
    private Transaction transactionMock;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        userId = 1L;
        wallet.setUserId(userId);
        wallet.setCreditAmount(BigDecimal.valueOf(1000));

        transactionMock = new Transaction();
        transactionMock.setUserId(userId);
        transactionMock.setPickup(null);
        transactionMock.setTransactionType(TransactionType.DEBIT);
        transactionMock.setAmount(BigDecimal.valueOf(500));

        walletRepository = mock(WalletRepository.class);
        transactionService = mock(TransactionService.class);

        walletService = new WalletServiceImpl(walletRepository, transactionService);
    }

    @Test
    void testGetWalletByUserId() {
        when(walletRepository.findByUserId(userId)).thenReturn(Optional.of(wallet));

        Optional<Wallet> retrievedWallet = walletService.getWalletByUserId(userId);

        assertTrue(retrievedWallet.isPresent());
        assertEquals(wallet, retrievedWallet.get());
        verify(walletRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetWalletByUserIdException() {
        when(walletRepository.findByUserId(userId)).thenThrow(new RuntimeException("Database connection error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> walletService.getWalletByUserId(userId));

        String expectedErrorMessage = "Error fetching wallet for user ID: " + userId;
        String expectedCauseMessage = "Database connection error";
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedCauseMessage, exception.getCause().getMessage());

        verify(walletRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testAddCreditSuccess() {
        BigDecimal creditAmount = BigDecimal.valueOf(500);

        when(walletRepository.findByUserId(userId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        walletService.addCredit(userId, creditAmount);

        assertEquals(BigDecimal.valueOf(1500), wallet.getCreditAmount());
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testAddCreditFailure() {
        BigDecimal creditAmount = BigDecimal.valueOf(500);

        when(walletRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> walletService.addCredit(userId, creditAmount));

        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void testUpdateCreditSufficientFunds() {
        BigDecimal deductionAmount = BigDecimal.valueOf(500);

        when(walletRepository.findByUserId(userId)).thenReturn(Optional.of(wallet));
        when(transactionService.createDebitTransaction(userId, deductionAmount, null)).thenReturn(transactionMock);

        walletService.updateCredit(userId, deductionAmount, null);

        assertEquals(BigDecimal.valueOf(500), wallet.getCreditAmount());
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testUpdateCreditInsufficientFunds() {
        BigDecimal deductionAmount = BigDecimal.valueOf(1500);

        when(walletRepository.findByUserId(userId)).thenReturn(Optional.of(wallet));

        assertThrows(RuntimeException.class, () -> walletService.updateCredit(userId, deductionAmount, null));

        assertEquals(BigDecimal.valueOf(1000), wallet.getCreditAmount());
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, never()).save(any(Wallet.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testGetTransactionsByUserId() {
        when(transactionService.getTransactionsByUserId(userId)).thenReturn(List.of());

        List<Transaction> transactions = walletService.getTransactionsByUserId(userId);

        assertNotNull(transactions);
        assertEquals(0, transactions.size());
    }

    @Test
    void testGetTransactionsByUserIdException() {

        when(transactionService.getTransactionsByUserId(userId)).thenThrow(new RuntimeException("Database connection error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> walletService.getTransactionsByUserId(userId));

        String expectedErrorMessage = "Error fetching transactions for user ID: " + userId;

        String expectedCauseMessage = "Database connection error";
        assertEquals(expectedErrorMessage, exception.getMessage());
        assertEquals(expectedCauseMessage, exception.getCause().getMessage());

        verify(transactionService, times(1)).getTransactionsByUserId(userId);
    }
}
