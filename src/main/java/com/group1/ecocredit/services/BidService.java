package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.User;

import java.util.List;

public interface BidService {

    Bid putWasteForBid(BidCreateRequest bidCreateRequest, User user);

    List<Bid> getAllActiveBids();

    List<Bid> getAllBids();

    void checkAndUpdateActiveBids();
}