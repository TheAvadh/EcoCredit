package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Range;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RangeRepository extends JpaRepository<Range, Integer> {
    Range findByMinValueLessThanEqualAndMaxValueGreaterThan(Integer minValue, Integer maxValue);

}
