package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.models.TransactionType;
import com.group1.ecocredit.repositories.TransactionRepository;
import com.group1.ecocredit.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction getTransactionFromPickup(Pickup pickup) {

        return transactionRepository.findByPickup(pickup).getFirst();
    }

    @Override
    public Transaction createDebitTransaction(Long userId, BigDecimal deductionAmount, Pickup pickup) {

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(deductionAmount);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setPickup(pickup);

        transactionRepository.save(transaction);

        return transaction;

    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {

        return transactionRepository.findByUserId(userId);
    }


}
