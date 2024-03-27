package com.group1.ecocredit.services;

import com.group1.ecocredit.models.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findByValue(String value);

    Category save(Category category);
}
