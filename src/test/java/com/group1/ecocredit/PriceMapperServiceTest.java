package com.group1.ecocredit;

import com.group1.ecocredit.repositories.CategoryPriceRepository;
import com.group1.ecocredit.services.PriceMapperService;
import com.group1.ecocredit.services.implementations.PriceMapperServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceMapperServiceTest {

    private PriceMapperService priceMapperService;

    @Mock
    private CategoryPriceRepository categoryPriceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceMapperService = PriceMapperServiceImpl.getInstance();
        categoryPriceRepository = mock(CategoryPriceRepository.class);
    }


    @Test
    void testGetInstanceReturnsSameInstance() {
        PriceMapperService instance1 = PriceMapperServiceImpl.getInstance();
        PriceMapperService instance2 = PriceMapperServiceImpl.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testGetInstanceReturnsNonNullInstance() {
        PriceMapperService instance = PriceMapperServiceImpl.getInstance();
        assertNotNull(instance);
    }

    @Test
    void testNullInputInGetPrice() {
        assertThrows(IllegalArgumentException.class, () -> priceMapperService.getPrice(null));
    }


    @Test
    void testEmptyCategoryPriceRepository() {
        when(categoryPriceRepository.findAll()).thenReturn(new ArrayList<>());
        HashMap<String, Float> priceMap = getPriceMap(priceMapperService);
        assertTrue(priceMap.isEmpty());
    }


    private HashMap<String, Float> getPriceMap(PriceMapperService priceMapperService) {
        try {
            java.lang.reflect.Field field = PriceMapperServiceImpl.class.getDeclaredField("priceMap");
            field.setAccessible(true);
            return (HashMap<String, Float>) field.get(priceMapperService);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
