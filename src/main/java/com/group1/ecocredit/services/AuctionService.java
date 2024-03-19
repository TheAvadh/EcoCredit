package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.DisplayBidRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.User;

import java.util.List;

public interface AuctionService {

    List<Bid> viewAllActiveBids();

    List<Bid> viewUserBids(Integer userId);

    Bid placeOrUpdateBid(DisplayBidRequest request, User user);

    /*Bid incrementBid(Long bidId);*/
}
