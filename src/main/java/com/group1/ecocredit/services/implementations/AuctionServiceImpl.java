package com.group1.ecocredit.services.implementations;


import com.group1.ecocredit.dto.DisplayBidRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.BidUser;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.BidService;
import com.group1.ecocredit.services.BidUserService;
import com.group1.ecocredit.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    BidService bidService;

    @Autowired
    BidUserService bidUserService;


    @Override
    public List<BidUser> viewAllActiveBids() {

        List<Bid> listBid = bidService.findAllActiveBids();
        List<BidUser> listBidUser = new ArrayList<>();

        for (Bid bid : listBid) {
            //Here I am using the BidUser just to ensure that only certain fields will be displayed to the recycler.
            //Do not save this BidUser object into the database, this just for retrieving purpose.
            BidUser bidUser = new BidUser();
            bidUser.setId(bid.getId());
            bidUser.setDate(bid.getDate());
            bidUser.setWaste_type(bid.getWaste().getCategory().getValue());
            bidUser.setWaste_weight(bid.getWaste().getWeight());
            bidUser.setBid_amount(bid.getBase_price());
            bidUser.setHighest_bid(bid.getTop_bid_amount());
            listBidUser.add(bidUser);
        }

        return listBidUser;
    }

    @Override
    public List<BidUser> viewUserBids(Integer userId) {
        return bidUserService.findByUserId(userId);
    }


    @Override
    public BidUser raiseBid(DisplayBidRequest request, User user) {

        BidUser bidUser = new BidUser();
        Long BidId = request.getBidId();
        Bid bid = bidService.findById(BidId);

        Double nextBid = bid.getTop_bid_amount() + rangeDifference(bid.getTop_bid_amount());

        Double enteredBid = request.getNewBidAmount();

        if (enteredBid != null) {
            processEnteredBid(bid, bidUser, user, enteredBid, nextBid);
        }
        else {
            processNullBid(bid, bidUser, user, nextBid);
        }

        return bidUser;
    }

    private void processEnteredBid(Bid bid, BidUser bidUser, User user, Double enteredBid, Double nextBid) {
        if (enteredBid >= nextBid) {
            bid.setTop_bid_amount(enteredBid);
            bid.setUser(user);
            saveBidAndBidUser(bid, bidUser, user, enteredBid);

        } else {
            throw new IllegalArgumentException("Entered amount should be greater than or equal to " + nextBid);
        }
    }

    private void processNullBid(Bid bid, BidUser bidUser, User user, Double nextBid) {
        bid.setTop_bid_amount(nextBid);
        bid.setUser(user);
        saveBidAndBidUser(bid, bidUser, user, nextBid);
    }

    private void saveBidAndBidUser(Bid bid, BidUser bidUser, User user, Double bidAmount) {
        bidUser.setBid_amount(bidAmount);
        bidUser.setDate(LocalDateTime.now());
        bidUser.setUser(user);
        bidUser.setIs_Active(bid.is_active());
        bidUser.setHighest_bid(bid.getTop_bid_amount());
        bidUser.setWaste_type(bid.getWaste().getCategory().getValue());
        bidUser.setWaste_weight(bid.getWaste().getWeight());
        bidUser.setBid(bid);

        bidUserService.save(bidUser);
        bidService.save(bid);
    }


    @Override
    public BidUser placeBid(Long bidId) {

        Bid bid = bidService.findById(bidId);
        if (bid.is_active()) {
            //Here I am using the BidUser just to ensure that only certain fields will be displayed to the recycler.
            //Do not save this BidUser object into the database, this just for retrieving purpose.
            BidUser storeBidUser = new BidUser();
            storeBidUser.setId(bid.getId());
            storeBidUser.setDate(bid.getDate());
            storeBidUser.setWaste_type(bid.getWaste().getCategory().getValue());
            storeBidUser.setWaste_weight(bid.getWaste().getWeight());
            storeBidUser.setBid_amount(bid.getBase_price());
            storeBidUser.setHighest_bid(bid.getTop_bid_amount());

            return storeBidUser;
        } else {
            throw new RuntimeException("The bid is no longer active");
        }
    }


    public static Integer rangeDifference(Double currentPrice) {
        if (currentPrice > 0 && currentPrice <= 20) {
            return 2;
        } else if (currentPrice > 20 && currentPrice <= 50) {
            return 5;
        } else if (currentPrice > 50 && currentPrice <= 100) {
            return 10;
        } else if (currentPrice > 100 && currentPrice <= 250) {
            return 25;
        } else if (currentPrice > 250 && currentPrice <= 500) {
            return 50;
        } else {
            return 100;
        }
    }

    public void discardActiveStatus(){
       List<BidUser> listBidUser = bidUserService.findByActive(true);
       for(BidUser bidUser:listBidUser){
           Long idBid = bidUser.getBid().getId();
           bidUser.setIs_Active(bidService.findById(idBid).is_active());
           bidUserService.save(bidUser);
       }
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    public void bidUserScheduler() {
        discardActiveStatus();
    }

}
