package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.CategoryPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryPriceRepository extends JpaRepository<CategoryPrice, Long> {

    @Query("SELECT cp FROM CategoryPrice cp WHERE cp.category.value = :categoryName")
    CategoryPrice findByCategoryName(@Param("categoryName") String categoryName);
}
