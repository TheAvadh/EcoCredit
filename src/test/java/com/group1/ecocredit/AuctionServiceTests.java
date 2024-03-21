package com.group1.ecocredit;

import com.group1.ecocredit.dto.DisplayBidRequest;
import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.BidRepository;
import com.group1.ecocredit.repositories.BidUserRepository;
import com.group1.ecocredit.services.implementations.AuctionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;


import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
public class AuctionServiceTests {
    @InjectMocks
    private AuctionServiceImpl auctionService;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private BidUserRepository bidUserRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewAllActiveBids() {
        // Mock dependencies
        Waste mockWaste = mock(Waste.class);
        Category mockCategory = mock(Category.class);
        when(mockWaste.getCategory()).thenReturn(mockCategory);
        when(mockCategory.getValue()).thenReturn("Plastic"); // Example category

        Bid bid = new Bid();
        bid.setId(1L);
        bid.setWaste(mockWaste);
        bid.setBase_price(100);
        bid.setTop_bid_amount(150);
        bid.setDate(LocalDateTime.now());
        bid.set_active(true);

        when(bidRepository.findAllActiveBids()).thenReturn(Arrays.asList(bid));

        // Execute the method to test
        List<BidUser> result = auctionService.viewAllActiveBids();

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(bid.getId(), result.get(0).getId());
        assertEquals("Plastic", result.get(0).getWaste_type()); // Verify the waste type is correctly set
        verify(bidRepository, times(1)).findAllActiveBids();
    }



    // Helper method to create a mock Bid object
    private Bid createMockBid() {
        Bid bid = new Bid();
        bid.setId(1L);
        bid.setDate(LocalDateTime.now());
        // Setup other necessary Bid properties
        return bid;
    }

    // Helper method to create a mock BidUser object
    private BidUser createMockBidUser() {
        BidUser bidUser = new BidUser();
        bidUser.setId(1L);
        User user = new User();
        user.setId(1);
        bidUser.setUser(user);
        // Setup other necessary BidUser properties
        return bidUser;
    }
}


