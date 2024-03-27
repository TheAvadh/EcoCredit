package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.TransactionRepository;
import com.group1.ecocredit.repositories.WalletRepository;
import com.group1.ecocredit.services.TransactionService;
import com.group1.ecocredit.services.WalletService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    @Transactional
    public Optional<Wallet> getWalletByUserId(Long userId) {
        try {
            return walletRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching wallet for user ID: " + userId, e);
        }
    }

    @Override
    @Transactional
    public void addCredit(Long userId, BigDecimal creditAmount) {
        try {
            Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);

            Wallet wallet = optionalWallet.orElseGet(() -> {
                Wallet newWallet = new Wallet();
                newWallet.setUserId(userId);
                return newWallet;
            });

            wallet.setCreditAmount(wallet.getCreditAmount().add(creditAmount));

            walletRepository.save(wallet);
        } catch (Exception e) {
            throw new RuntimeException("Error crediting points for user ID: " + userId, e);
        }
    }

    @Override
    @Transactional
    public void updateCredit(Long userId, BigDecimal deductionAmount, Pickup pickup) {
        try {
            Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);
            Wallet wallet = optionalWallet.orElseThrow(() ->
                    new RuntimeException("Wallet Details not found for user ID: " + userId));

            BigDecimal currentCreditAmount = wallet.getCreditAmount();

            if (currentCreditAmount.compareTo(deductionAmount) >= 0) {
                wallet.setCreditAmount(currentCreditAmount.subtract(deductionAmount));
                walletRepository.save(wallet);

                transactionService.createDebitTransaction(userId, deductionAmount, pickup);
            } else {
                throw new RuntimeException("Insufficient credit for user ID: " + userId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error making payment for user ID: " + userId, e);
        }
    }

    @Override
    @Transactional
    public List<Transaction> getTransactionsByUserId(Long userId) {

        try{
            return transactionService.getTransactionsByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching transactions for user ID: " + userId, e);
        }
    }

    @Override
    @Transactional
    public void createWalletForUser(User user) {

        Wallet wallet = new Wallet();
        wallet.setUserId(Long.valueOf(user.getId()));
        walletRepository.save(wallet);
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }
}
