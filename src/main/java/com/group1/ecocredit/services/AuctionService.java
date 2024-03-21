package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.DisplayBidRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.BidUser;
import com.group1.ecocredit.models.User;

import java.util.List;

public interface AuctionService {

    List<BidUser> viewAllActiveBids();

    List<BidUser> viewUserBids(Integer userId);

    BidUser raiseBid (DisplayBidRequest request, User user);

    BidUser placeBid (Long bidId);

}
