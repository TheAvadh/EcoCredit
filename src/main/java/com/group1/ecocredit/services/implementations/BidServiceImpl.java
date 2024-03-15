package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.CategoryPrice;
import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.BidRepository;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.BidService;


import java.time.LocalDateTime;
import java.util.Optional;

public class BidServiceImpl implements BidService {

    BidRepository bidRepository;
    WasteRepository wasteRepository;
    CategoryPrice categoryPrice;
    @Override
    public Bid putWasteForBid(Long wasteId) {


        Optional<Bid> optionalBid = bidRepository.findByWasteId(wasteId);
        Optional<Waste> optionalWaste = wasteRepository.findById(wasteId);

        if (optionalBid.get() != null){
            System.out.println("The waste is already auctioned");
            Bid bid = bidRepository.findByWasteId(wasteId).get();
            return bid;
        }
        else{
            if(optionalWaste.isEmpty()){
                System.out.println("No waste with given waste id present");
            }

            else{
                Waste waste = wasteRepository.findById(wasteId).get();

                Bid bid = new Bid();
                bid.setBase_price(categoryPrice.getValue(waste.getCategory().getId()));
                bid.setWaste(waste);
                bid.setTop_bid_amount(bid.getBase_price());
                bid.set_active(true);

                return bid;
            }
        }

        return null;
    }
}
