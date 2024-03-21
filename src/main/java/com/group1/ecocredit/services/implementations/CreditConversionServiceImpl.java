package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.enums.Currency;
import com.group1.ecocredit.services.CreditConversionService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreditConversionServiceImpl implements CreditConversionService {

    Map<Currency, Float> conversionRates;
    @PostConstruct
    private void generateMappings() {
        conversionRates = new HashMap<>();
        conversionRates.put(Currency.CAD, 1.0f);
        conversionRates.put(Currency.EUR, 1.5f);
        conversionRates.put(Currency.USD, 1.2f);
        conversionRates.put(Currency.JPY, 0.7f);
    }

    @Override
    public Double convert(Long amount, Currency currency) {

        return Math.ceil(amount * conversionRates.get(currency));
    }
}
