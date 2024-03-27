package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.CategoryPrice;
import com.group1.ecocredit.repositories.CategoryPriceRepository;
import com.group1.ecocredit.services.CategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryPriceServiceImpl implements CategoryPriceService {

    @Autowired
    private CategoryPriceRepository categoryPriceRepository;

    @Override
    public Optional<CategoryPrice> findByCategoryId(int categoryId) {
        return categoryPriceRepository.findByCategoryId(categoryId);
    }

    @Override
    public CategoryPrice findByCategoryName(String categoryName) {
        return categoryPriceRepository.findByCategoryName(categoryName);
    }

    @Override
    public CategoryPrice save(CategoryPrice categoryPrice) {
        return categoryPriceRepository.save(categoryPrice);
    }

    @Override
    public List<CategoryPrice> findAll() {
        return categoryPriceRepository.findAll();
    }
}
