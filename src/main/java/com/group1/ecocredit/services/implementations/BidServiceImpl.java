package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.CategoryPrice;
import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.BidRepository;
import com.group1.ecocredit.repositories.CategoryPriceRepository;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.AuthenticationService;
import com.group1.ecocredit.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Configuration
@EnableScheduling
public class BidServiceImpl implements BidService {

    @Autowired
    BidRepository bidRepository;
    @Autowired
    WasteRepository wasteRepository;
    @Autowired
    CategoryPriceRepository categoryPriceRepository;


    @Override
    public Bid putWasteForBid(BidCreateRequest bidCreateRequest) {


        Optional<Bid> optionalBid = bidRepository.findByWasteId(bidCreateRequest.getWasteId());
        Optional<Waste> optionalWaste = wasteRepository.findById(bidCreateRequest.getWasteId());
        LocalDateTime bidTime = LocalDateTime.parse(bidCreateRequest.getDateTime());

        if (optionalBid.isPresent()){
            System.out.println("The waste is already auctioned");
            Bid bid = bidRepository.findByWasteId(bidCreateRequest.getWasteId()).get();
            return bid;
        }

        if(optionalWaste.isEmpty() == true){
            throw new NoSuchElementException("No waste with given waste id present");
        }

        if(LocalDateTime.now().isAfter(bidTime)){
            throw new IllegalArgumentException("You can't create bid in past");
        }

        else {

            Waste waste = wasteRepository.findById(bidCreateRequest.getWasteId()).get();
//          Base Price = category base price multiply by weight of the waste.
            Optional<CategoryPrice> categoryPrice = categoryPriceRepository.findByCategoryId(waste.getCategory().getId());
            Float basePriceF = (float)categoryPrice.get().getValue() * waste.getWeight();
            Double basePrice = (double)Math.round(basePriceF);

            Bid bid = new Bid();
            bid.setBase_price(basePrice);
            bid.setWaste(waste);
            bid.setTop_bid_amount(basePrice);
            bid.setDate(bidTime);

            bidRepository.save(bid);

            System.out.println("Waste is now available for auction");
            return  bid;
        }
    }

    @Override
    public void activateBids() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<Bid> bidsToActivate = bidRepository.findBidsToActivate();

        for (Bid bid : bidsToActivate) {

            LocalDateTime bidCreationDateTime = bid.getDate();
            if(currentDateTime.isAfter(bidCreationDateTime))
            {
                bid.set_active(true);
                bidRepository.save(bid);
                System.out.println("Bid with ID " + bid.getId() + " has been activated.");
            }
        }

    }

    @Override
    public void expireBids() {
        List<Bid> activeBids = bidRepository.findByIsActive(true);
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Bid bid : activeBids) {
            LocalDateTime bidCreationDateTime = bid.getDate();
            LocalDateTime bidExpiryDateTime = bidCreationDateTime.plusHours(24); // Bid expires after 24 hours

            //Note that bid will only expire if any user has participated else it will stay until the first bidder will bid
            if (currentDateTime.isAfter(bidExpiryDateTime) && bid.getUser()!=null) {
                bid.set_active(false);
                bid.setSold(true);
                bidRepository.save(bid);
                System.out.println("Bid with ID " + bid.getId() + " has expired and deactivated.");
            }
        }
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    public void bidSchedular() {
        activateBids();
        expireBids();
    }


    @Override
    public List<Bid> getAllActiveBids() {
        List<Bid> bidList = bidRepository.findByIsActive(true);
        return bidList;
    }

    @Override
    public List<Bid> getAllBids() {
        List<Bid> bidList = bidRepository.findAll();
        return bidList;
    }

    @Service
    public static class WasteServiceImpl implements AuthenticationService.WasteService {
        @Autowired
        private WasteRepository wasteRepository;

        @Override
        public boolean updateWeight(Long id, WasteUpdateRequest request) {
            var optionalWaste = wasteRepository.findById(id);
            if (optionalWaste.isEmpty()) {
                return false;
            }
            var waste = optionalWaste.get();
            waste.setWeight(request.getWeight());
            wasteRepository.save(waste);
            return true;
        }
    }
}
