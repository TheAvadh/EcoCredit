package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.CategoryPrice;
import com.group1.ecocredit.repositories.CategoryPriceRepository;
import com.group1.ecocredit.services.PriceMapperService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PriceMapperServiceImpl implements PriceMapperService {

    private final HashMap<String, Float> priceMap;

    @Autowired
    private CategoryPriceRepository categoryPriceRepository;

    private static PriceMapperService instance;
    private PriceMapperServiceImpl() {
        priceMap = new HashMap<>();
    }

    @PostConstruct
    private void initializePriceMap() {
        List<CategoryPrice> categoryPrices = categoryPriceRepository.findAll();
        for (CategoryPrice categoryPrice : categoryPrices) {
            this.priceMap.put(categoryPrice.getCategory().getValue(), categoryPrice.getValue());
        }
    }


    public static PriceMapperService getInstance() {
        if(instance == null) {
            instance = new PriceMapperServiceImpl();
        }

        return instance;
    }

    public float getPrice(String category) {
        return priceMap.get(category);
    }

}
