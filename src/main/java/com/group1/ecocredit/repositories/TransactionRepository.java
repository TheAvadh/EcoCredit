package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findByUserId(Long userId);

}
