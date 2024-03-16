package com.group1.ecocredit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletRequest {
    private Long userId;
    private BigDecimal creditAmount;
    private BigDecimal deductionAmount;
}
