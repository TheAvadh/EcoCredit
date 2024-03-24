package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByValue(String value);
}