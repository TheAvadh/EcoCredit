package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {

    @Query("SELECT b FROM Bid b WHERE b.waste.id = :wasteId")
    Optional<Bid> findByWasteId(long wasteId);

    @Query("SELECT b FROM Bid b WHERE b.is_active = :isActive")
    List<Bid> findByIsActive(boolean isActive);

}