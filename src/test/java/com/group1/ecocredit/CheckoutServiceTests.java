package com.group1.ecocredit;

import com.group1.ecocredit.enums.Currency;
import com.group1.ecocredit.models.Wallet;
import com.group1.ecocredit.services.CreditConversionService;
import com.group1.ecocredit.services.WalletService;
import com.group1.ecocredit.services.implementations.CheckoutServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTests {
    @Mock
    WalletService walletService;

    @Mock
    CreditConversionService creditConversionService;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCanUserPayByWallet_WhenWalletIsEmpty() {
        when(walletService.getWalletByUserId(anyLong()))
                .thenReturn(Optional.empty());

        var amountInCAD = 10L;
        var result = checkoutService.canUserPayByWallet(USER_ID, amountInCAD);

        Assertions.assertFalse(result);
    }

    @Test
    void testCanUserPayByWallet_WhenNotEnoughCredits() {
        var wallet = new Wallet();
        var credits = 5.0;
        wallet.setUserId(USER_ID);
        wallet.setCreditAmount(BigDecimal.valueOf(credits));
        when(walletService.getWalletByUserId(anyLong()))
                .thenReturn(Optional.of(wallet));

        var amountInCredits = 10.0;
        when(creditConversionService.convert(anyDouble(), any(Currency.class)))
                .thenReturn(amountInCredits);

        var amountInCAD = 10L;
        var result = checkoutService.canUserPayByWallet(USER_ID, amountInCAD);

        Assertions.assertFalse(result);
    }

    @Test
    void testCanUserPayByWallet_WhenCreditsEqualsAmount() {
        var wallet = new Wallet();
        var credits = 10.0;
        wallet.setUserId(USER_ID);
        wallet.setCreditAmount(BigDecimal.valueOf(credits));
        when(walletService.getWalletByUserId(anyLong())).thenReturn(Optional.of(wallet));

        var amountInCredits = 10.0;
        when(creditConversionService.convert(anyDouble(), any(Currency.class)))
                .thenReturn(amountInCredits);

        var amountInCAD = 10L;
        var result = checkoutService.canUserPayByWallet(USER_ID, amountInCAD);

        Assertions.assertTrue(result);
    }

    @Test
    void testCanUserPayByWallet_WhenCreditsIsGreaterThanAmount() {
        var wallet = new Wallet();
        var credits = 15.0;
        wallet.setUserId(USER_ID);
        wallet.setCreditAmount(BigDecimal.valueOf(credits));
        when(walletService.getWalletByUserId(anyLong())).thenReturn(Optional.of(wallet));

        var amountInCredits = 10.0;
        when(creditConversionService.convert(anyDouble(), any(Currency.class)))
                .thenReturn(amountInCredits);

        var amountInCAD = 10L;
        var result = checkoutService.canUserPayByWallet(USER_ID, amountInCAD);

        Assertions.assertTrue(result);
    }

    @Test
    void testCalculatePickupCharge_WhenCreditsIsGreaterThanAmount() {
        var minPickupValue = 1;
        var result = checkoutService.calculatePickupCharge();
        Assertions.assertTrue(result > minPickupValue);
    }
}
