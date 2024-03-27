package com.group1.ecocredit;
import com.group1.ecocredit.enums.Currency;
import com.group1.ecocredit.services.implementations.CreditConversionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditConversionServiceTests {
    private CreditConversionServiceImpl creditConversionService;

    @BeforeEach
    void setUp() {
        creditConversionService = new CreditConversionServiceImpl();
        // Manually invoke the PostConstruct method to initialize conversion rates
        creditConversionService.generateMappings();
    }
    @Test
    void convert_CADtoCAD_ShouldReturnSameAmount() {
        double converted = creditConversionService.convert(100.0, Currency.CAD);
        assertEquals(100.0, converted);
    }

    @Test
    void convert_CADtoEUR_ShouldReturnCorrectAmount() {
        double converted = creditConversionService.convert(100.0, Currency.EUR);
        assertEquals(150.0, converted);
    }

    @Test
    void convert_CADtoUSD_ShouldReturnCorrectAmount() {
        double converted = creditConversionService.convert(100.0, Currency.USD);
        assertEquals(121.0, converted);
    }

    @Test
    void convert_CADtoJPY_ShouldReturnCorrectAmount() {
        double converted = creditConversionService.convert(100.0, Currency.JPY);
        assertEquals(70.0, converted);
    }

    @Test
    void convert_WithRounding_ShouldReturnCeiledAmount() {
        creditConversionService.conversionRates.put(Currency.EUR, 1.55f);
        double converted = creditConversionService.convert(50.0, Currency.EUR);
        assertEquals(78.0, converted);
    }

}
