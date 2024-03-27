package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.CategoryPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface CategoryPriceRepository extends JpaRepository<CategoryPrice, Integer> {
    Optional<CategoryPrice> findByCategoryId(int category_id);

    @Query("SELECT cp FROM CategoryPrice cp WHERE cp.category.value = :categoryName")
    CategoryPrice findByCategoryName(@Param("categoryName") String categoryName);
}
