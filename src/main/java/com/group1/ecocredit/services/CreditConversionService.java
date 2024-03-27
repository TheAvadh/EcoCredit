package com.group1.ecocredit.services;

import com.group1.ecocredit.enums.Currency;

import java.math.BigDecimal;

public interface CreditConversionService {

    Double convert(Double amount, Currency currency);
}
