package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
