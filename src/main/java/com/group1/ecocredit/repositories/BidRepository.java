package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {

    Optional<Bid> findByWasteId(long wasteID);

}