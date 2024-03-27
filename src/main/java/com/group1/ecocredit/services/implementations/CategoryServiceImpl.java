package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Category;
import com.group1.ecocredit.repositories.CategoryRepository;
import com.group1.ecocredit.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findByValue(String value) {
        return categoryRepository.findByValue(value);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
