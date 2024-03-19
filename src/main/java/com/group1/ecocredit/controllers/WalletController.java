package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.WalletRequest;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.models.Wallet;
import com.group1.ecocredit.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/getCredit/{userId}")
    public ResponseEntity<?> getUserWalletDetails(@PathVariable Long userId) {

        Optional<Wallet> optionalWallet = walletService.getWalletByUserId(userId);

        if (optionalWallet.isPresent()) {
            Wallet userWallet = optionalWallet.get();
            return ResponseEntity.ok(userWallet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addCredit/{userId}")
    public ResponseEntity<?> addCreditAmount(@PathVariable Long userId, @RequestBody WalletRequest walletRequest) {
        walletService.addCredit(walletRequest.getUserId(), walletRequest.getCreditAmount());

        return ResponseEntity.ok("Points credited successfully for user ID: " + userId);
    }

    @PutMapping("/updateCredit")
    public ResponseEntity<?> updateCreditAmount(@RequestBody WalletRequest walletRequest) {
        try {
            walletService.updateCredit(walletRequest.getUserId(), walletRequest.getDeductionAmount());
            return ResponseEntity.ok("Payment successful for user ID: " + walletRequest.getUserId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment for user ID: " + walletRequest.getUserId());
        }
    }

    @GetMapping("/getTransactions/{userId}")
    public ResponseEntity<?> getUserTransactions(@PathVariable Long userId) {
        List<Transaction> transactions = walletService.getTransactionsByUserId(userId);

        if (!transactions.isEmpty()) {
            return ResponseEntity.ok(transactions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
