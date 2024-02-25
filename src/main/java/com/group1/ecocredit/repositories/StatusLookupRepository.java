package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.StatusLookup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusLookupRepository extends JpaRepository<StatusLookup, Long> {
}
