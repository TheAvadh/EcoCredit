package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Integer> {
    @Query("SELECT b FROM Bid b WHERE b.is_active = true")
    List<Bid> findAllActiveBids();

    Bid findById(Long bidId);


}
