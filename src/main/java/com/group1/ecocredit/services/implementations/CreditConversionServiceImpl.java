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

    public Map<Currency, Float> conversionRates;

    private static final float RATE_CAD_TO_CAD = 1.0f;
    private static final float RATE_CAD_TO_EUR = 1.5f;
    private static final float RATE_CAD_TO_USD = 1.2f;
    private static final float RATE_CAD_TO_JPY = 0.7f;

    @PostConstruct
    public void generateMappings() {
        conversionRates = new HashMap<>();
        conversionRates.put(Currency.CAD, RATE_CAD_TO_CAD);
        conversionRates.put(Currency.EUR, RATE_CAD_TO_EUR);
        conversionRates.put(Currency.USD, RATE_CAD_TO_USD);
        conversionRates.put(Currency.JPY, RATE_CAD_TO_JPY);
    }

    @Override
    public Double convert(Double amount, Currency currency) {

        return Math.ceil(amount * conversionRates.get(currency));
    }
}
