package com.group1.ecocredit.services.implementations;


import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.BidService;

import java.util.Optional;

public class BidServiceImpl implements BidService {

    WasteRepository wasteRepository;

    @Override
    public String putWasteForBid(Long wasteId) {

            Optional<Waste> waste = wasteRepository.findById(wasteId);

            if (waste == null){
                return "No Waste Found!";
            }
            else {


                return "Waste is updated for Bid";
            }

        }
}
