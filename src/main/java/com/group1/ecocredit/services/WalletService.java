package com.group1.ecocredit.services;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.models.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WalletService {
    Optional<Wallet> getWalletByUserId(Long userId);

    void addCredit(Long userId, BigDecimal creditAmount);

    void updateCredit(Long userId, BigDecimal deductionAmount, Pickup pickup);

    List<Transaction> getTransactionsByUserId(Long userId);

    void createWalletForUser(User user);

    Wallet save (Wallet wallet);

}
