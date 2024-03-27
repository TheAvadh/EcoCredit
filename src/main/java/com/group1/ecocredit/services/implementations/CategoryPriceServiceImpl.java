package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.CategoryPrice;
import com.group1.ecocredit.repositories.CategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryPriceServiceImpl implements com.group1.ecocredit.services.CategoryPriceService {

    @Autowired
    private CategoryPriceService categoryPriceRepository;

    @Override
    public Optional<CategoryPrice> findByCategoryId(int categoryId) {
        return categoryPriceRepository.findByCategoryId(categoryId);
    }

    @Override
    public CategoryPrice findByCategoryName(String categoryName) {
        return categoryPriceRepository.findByCategoryName(categoryName);
    }
}
