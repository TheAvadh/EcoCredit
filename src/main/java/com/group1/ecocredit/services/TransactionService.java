package com.group1.ecocredit.services;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction getTransactionFromPickup(Pickup pickup);

    Transaction createDebitTransaction(Long userId, BigDecimal deductionAmount, Pickup pickup);
    List<Transaction> getTransactionsByUserId(Long userId);
}
