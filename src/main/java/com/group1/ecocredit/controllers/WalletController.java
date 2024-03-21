package com.group1.ecocredit.controllers;

import com.group1.ecocredit.dto.WalletRequest;
import com.group1.ecocredit.models.Transaction;
import com.group1.ecocredit.models.Wallet;
import com.group1.ecocredit.services.JWTService;
import com.group1.ecocredit.services.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private JWTService jwtService;

    private Integer getUserId(HttpServletRequest request) {

        return Integer.parseInt(jwtService.extractUserID(request.getHeader("Authorization")));
    }

    @GetMapping("/getCredit")
    public ResponseEntity<?> getUserWalletDetails(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Long userId = getUserId(request).longValue();

        Optional<Wallet> optionalWallet = walletService.getWalletByUserId(userId);

        if (optionalWallet.isPresent()) {
            Wallet userWallet = optionalWallet.get();
            return ResponseEntity.ok(userWallet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addCredit")
    public ResponseEntity<?> addCreditAmount(HttpServletRequest request, @RequestBody WalletRequest walletRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Long userId = getUserId(request).longValue();

        walletService.addCredit(walletRequest.getUserId(), walletRequest.getCreditAmount());

        return ResponseEntity.ok("Points credited successfully for user ID: " + userId);
    }

    @PutMapping("/updateCredit")
    public ResponseEntity<?> updateCreditAmount(@RequestBody WalletRequest walletRequest) {
        try {
            walletService.updateCredit(walletRequest.getUserId(), walletRequest.getDeductionAmount(), walletRequest.getPickup());
            return ResponseEntity.ok("Payment successful for user ID: " + walletRequest.getUserId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment for user ID: " + walletRequest.getUserId());
        }
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<?> getUserTransactions(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Long userId = getUserId(request).longValue();

        List<Transaction> transactions = walletService.getTransactionsByUserId(userId);

        if (!transactions.isEmpty()) {
            return ResponseEntity.ok(transactions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
