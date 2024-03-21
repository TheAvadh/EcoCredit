package com.group1.ecocredit.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StripeChargeDto {

    private BigDecimal amount;
    private String defaultPrice;
}
