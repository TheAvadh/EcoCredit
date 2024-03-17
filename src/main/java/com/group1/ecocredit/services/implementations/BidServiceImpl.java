package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.models.CategoryPrice;
import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.BidRepository;
import com.group1.ecocredit.repositories.CategoryPriceRepository;
import com.group1.ecocredit.repositories.CategoryRepository;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.BidService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
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

        if (optionalBid.isPresent()){
            System.out.println("The waste is already auctioned");
            Bid bid = bidRepository.findByWasteId(bidCreateRequest.getWasteId()).get();
            return bid;
        }
        else{
            if(optionalWaste.isEmpty() == true){
                System.out.println("No waste with given waste id present");
                return null;
            }

            else{
                Waste waste = wasteRepository.findById(bidCreateRequest.getWasteId()).get();
//                Base Price = category base price multiply by weight of the waste.
                Optional<CategoryPrice> categoryPrice = categoryPriceRepository.findByCategoryId(waste.getCategory().getId());
                Double basePrice = categoryPrice.get().getValue() * waste.getWeight();

                Bid bid = new Bid();
                bid.setBase_price(basePrice);
                bid.setWaste(waste);
                bid.setTop_bid_amount(basePrice);
                bid.set_active(true);
                bid.setDate(LocalDateTime.parse(bidCreateRequest.getDateTime()));

                bidRepository.save(bid);

                System.out.println("Waste is now available for auction");
                return  bid;
            }
        }

    }

    @Override
    @Scheduled(fixedRate = 3600000) // Runs every hour (3600000 milliseconds)
    public void checkAndUpdateActiveBids() {
        List<Bid> activeBids = bidRepository.findByIsActive(true);
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Bid bid : activeBids) {
            LocalDateTime bidCreationDateTime = bid.getDate();
            LocalDateTime bidExpiryDateTime = bidCreationDateTime.plusHours(24); // Bid expires after 24 hours

            if (currentDateTime.isAfter(bidExpiryDateTime)) {
                bid.set_active(false);
                bidRepository.save(bid);
                System.out.println("Bid with ID " + bid.getId() + " has expired and deactivated.");
            }
        }
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
}
