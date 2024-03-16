package com.group1.ecocredit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.BidRepository;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.implementations.BidServiceImpl;
import com.group1.ecocredit.dto.BidCreateRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
public class BidServiceTests {
    @Mock
    private BidRepository bidRepository;

    @Mock
    private WasteRepository wasteRepository;

    @Mock
    private CategoryPrice categoryPrice;

    @InjectMocks
    private BidServiceImpl bidService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenWasteAlreadyAuctioned_thenReturnExistingBid() {
        BidCreateRequest request = new BidCreateRequest();
        request.setWasteId(1L);
        Bid existingBid = new Bid();

        when(bidRepository.findByWasteId(request.getWasteId())).thenReturn(Optional.of(existingBid));

        Bid result = bidService.putWasteForBid(request, new User());
        assertNotNull(result);
        assertEquals(existingBid, result);
    }

    @Test
    void whenNoWasteFound_thenReturnNull() {
        BidCreateRequest request = new BidCreateRequest();
        request.setWasteId(999L); // Non-existent waste ID

        when(wasteRepository.findById(request.getWasteId())).thenReturn(Optional.empty());

        Bid result = bidService.putWasteForBid(request, new User());
        assertNull(result);
    }

    @Test
    void whenValidRequestAndWasteExists_thenCreateBid() {
        BidCreateRequest request = new BidCreateRequest();
        request.setWasteId(1L);
        request.setDateTime("2024-03-16T10:00:00");

        Waste waste = new Waste();
        waste.setId(1L);
        waste.setWeight(10.0);

        when(wasteRepository.findById(request.getWasteId())).thenReturn(Optional.of(waste));
        when(bidRepository.findByWasteId(request.getWasteId())).thenReturn(Optional.empty());
        when(categoryPrice.getValue(waste.getCategory().getId())).thenReturn(100.0); // Assume category base price

        Bid result = bidService.putWasteForBid(request, new User());

        assertNotNull(result);
        assertEquals(1000.0, result.getBase_price()); // Assuming base price calculation logic
    }




}
