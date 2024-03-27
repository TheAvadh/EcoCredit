package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.CategoryPrice;
import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.BidRepository;
import com.group1.ecocredit.services.CategoryPriceService;
import com.group1.ecocredit.services.WasteService;
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
public class BidServiceImpl implements com.group1.ecocredit.services.BidService {

    @Autowired
    BidRepository bidRepository;
    @Autowired
    WasteService wasteService;
    @Autowired
    CategoryPriceService categoryPriceService;

    private int expireHours=24;

    @Override
    public Bid putWasteForBid(BidCreateRequest bidCreateRequest) {


        Optional<Bid> optionalBid = bidRepository.findByWasteId(bidCreateRequest.getWasteId());
        Optional<Waste> optionalWaste = wasteService.findById(bidCreateRequest.getWasteId());
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

            Waste waste = wasteService.findById(bidCreateRequest.getWasteId()).get();
//          Base Price = category base price multiply by weight of the waste.
            Optional<CategoryPrice> categoryPrice = categoryPriceService.findByCategoryId(waste.getCategory().getId());
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
            LocalDateTime bidExpiryDateTime = bidCreationDateTime.plusHours(expireHours); // Bid expires after 24 hours

            //Note that bid will only expire if any user has participated else it will stay until the first bidder will bid
            if (currentDateTime.isAfter(bidExpiryDateTime) && bid.getUser()!=null) {
                bid.set_active(false);
                bid.setSold(true);
                bidRepository.save(bid);
                System.out.println("Bid with ID " + bid.getId() + " has expired and deactivated.");
            }
        }
    }

    @Override
    public Optional<Bid> findByWasteId(long wasteId) {
        return bidRepository.findByWasteId(wasteId);
    }

    @Override
    public List<Bid> findBidsToActivate() {
        return bidRepository.findBidsToActivate();
    }

    @Override
    public List<Bid> findAllActiveBids() {
        return bidRepository.findAllActiveBids();
    }

    @Override
    public Bid findById(Long bidId) {
        return bidRepository.findById(bidId);
    }

    @Scheduled(fixedRate = 2000) // Run every minute
    public void bidSchedular() {
        activateBids();
        expireBids();
    }


    @Override
    public List<Bid> getAllActiveBids() {
        return bidRepository.findByIsActive(true);
    }

    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    @Override
    public Bid save(Bid bid) { return bidRepository.save(bid); }
}
