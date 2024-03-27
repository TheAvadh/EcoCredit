package com.group1.ecocredit.services;

import com.group1.ecocredit.models.CategoryPrice;

import java.util.List;
import java.util.Optional;

public interface CategoryPriceService {
    Optional<CategoryPrice> findByCategoryId(int categoryId);
    CategoryPrice findByCategoryName(String categoryName);
    CategoryPrice save(CategoryPrice categoryPrice);

    List<CategoryPrice> findAll();
}
