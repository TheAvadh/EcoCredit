package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.models.Bid;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidService {

    Bid putWasteForBid(BidCreateRequest bidCreateRequest);

    List<Bid> getAllActiveBids();

    List<Bid> getAllBids();

    void activateBids();

    void expireBids();

    Optional<Bid> findByWasteId(long wasteId);

    List<Bid> findBidsToActivate();

    List<Bid> findAllActiveBids();

    Bid findById(Long bidId);

    Bid save(Bid bid);
}
