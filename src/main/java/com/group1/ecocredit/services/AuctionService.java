package com.group1.ecocredit.services;

import com.group1.ecocredit.models.Bid;

import java.util.List;

public interface AuctionService {

    List<Bid> viewAllActiveBids();

    List<Bid> viewUserBids(Integer userId);

    Bid placeOrUpdateBid(Integer userId, Long bidId, Integer newBidAmount);

    Bid incrementBid(Long bidId);
}
