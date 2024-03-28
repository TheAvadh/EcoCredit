package com.group1.ecocredit;

import com.group1.ecocredit.dto.DisplayBidRequest;
import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.services.BidService;
import com.group1.ecocredit.services.BidUserService;
import com.group1.ecocredit.services.WalletService;
import com.group1.ecocredit.services.implementations.AuctionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.List;

public class AuctionServiceTests {
    private AuctionServiceImpl auctionService;
    private BidService bidService;
    private BidUserService bidUserService;
    private WalletService walletService;

    @BeforeEach
    void setUp() {
        bidService = mock(BidService.class);
        bidUserService = mock(BidUserService.class);
        walletService = mock(WalletService.class);
        auctionService = new AuctionServiceImpl(bidService, bidUserService,
                walletService);
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
        bid.setBase_price(100d);
        bid.setTop_bid_amount(150d);
        bid.setDate(LocalDateTime.now());
        bid.set_active(true);

        when(bidService.findAllActiveBids()).thenReturn(Arrays.asList(bid));

        // Execute the method to test
        List<BidUser> result = auctionService.viewAllActiveBids();

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(bid.getId(), result.get(0).getId());
        assertEquals("Plastic", result.get(0).getWaste_type());
        verify(bidService, times(1)).findAllActiveBids();
    }

    @Test
    void testViewUserBids() {
        // Setup mock data
        Integer userId = 1;
        BidUser bidUser = createMockBidUser();
        when(bidUserService.findByUserId(userId)).thenReturn(Arrays.asList(bidUser));

        // Execute the method to test
        List<BidUser> result = auctionService.viewUserBids(userId);

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(userId, result.get(0).getUser().getId());
        verify(bidUserService, times(1)).findByUserId(userId);
    }

    @Test
    void testRaiseBid() {
        // Setup mock data
        DisplayBidRequest request = new DisplayBidRequest();
        request.setBidId(1L);
        request.setNewBidAmount(30d);

        User user = User.builder()
                .id(1)
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .isEnabled(true)
                .role(Role.USER)
                .build();

        // Setup mock Waste and Category
        Waste mockWaste = mock(Waste.class);
        Category mockCategory = mock(Category.class);
        when(mockWaste.getCategory()).thenReturn(mockCategory);

        // Setup mock Bid
        Bid bid = createMockBid();
        bid.setTop_bid_amount(20d);
        bid.setWaste(mockWaste);

        // Setup Wallet
        Wallet wallet = new Wallet();
        wallet.setCreditAmount(BigDecimal.valueOf(50.0));
        when(walletService.getWalletByUserId(anyLong())).thenReturn(Optional.of(wallet));

        // Mock repository interactions
        when(bidService.findById(request.getBidId())).thenReturn(bid);
        when(bidService.save(any(Bid.class))).thenAnswer(i -> i.getArguments()[0]);
        when(bidUserService.save(any(BidUser.class))).thenAnswer(i -> i.getArguments()[0]);

        // Execute the method to test
        BidUser result = auctionService.raiseBid(request, user);

        // Assertions
        assertNotNull(result);
        assertEquals(request.getNewBidAmount(), result.getBid_amount());
        assertEquals(bid.getTop_bid_amount(), result.getHighest_bid());
        verify(bidService, times(1)).findById(request.getBidId());
        verify(bidService, times(1)).save(any(Bid.class));
        verify(bidUserService, times(1)).save(any(BidUser.class));
    }



    @Test
    void testPlaceBid() {
        // Setup mock data
        Long bidId = 1L;

        // Mock Waste and Category, assuming Category is a property accessed within Waste
        Waste mockWaste = mock(Waste.class);
        Category mockCategory = mock(Category.class);
        when(mockWaste.getCategory()).thenReturn(mockCategory);

        Bid bid = createMockBid();
        bid.set_active(true);
        bid.setWaste(mockWaste);

        when(bidService.findById(bidId)).thenReturn(bid);

        // Execute the method to test
        BidUser result = auctionService.placeBid(bidId);

        // Assertions
        assertNotNull(result);
        assertEquals(bid.getId(), result.getId());
        assertEquals(bid.getBase_price(), result.getBid_amount());
        verify(bidService, times(1)).findById(bidId);
    }

    @Test
    void testRaiseBidWithInsufficientAmount() {
        // Setup
        DisplayBidRequest request = new DisplayBidRequest();
        request.setBidId(1L);
        request.setNewBidAmount(5d);

        User user = User.builder()
                .id(1)
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .isEnabled(true)
                .role(Role.USER)
                .build();

        Waste mockWaste = mock(Waste.class);
        Category mockCategory = mock(Category.class);
        when(mockWaste.getCategory()).thenReturn(mockCategory);

        Bid bid = createMockBid();
        bid.setTop_bid_amount(20d);
        bid.setWaste(mockWaste);
        when(bidService.findById(request.getBidId())).thenReturn(bid);

        Wallet wallet = new Wallet();
        wallet.setCreditAmount(BigDecimal.valueOf(20.0));
        when(walletService.getWalletByUserId(anyLong())).thenReturn(Optional.of(wallet));

        // Execute & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            auctionService.raiseBid(request, user);
        });

        assertTrue(exception.getMessage().contains("Entered amount should be greater than or equal to"));
    }

    @Test
    void testViewUserBidsWithNoBids() {
        Integer userId = 2;
        when(bidUserService.findByUserId(userId)).thenReturn(new ArrayList<>());

        List<BidUser> result = auctionService.viewUserBids(userId);

        assertTrue(result.isEmpty());
        verify(bidUserService, times(1)).findByUserId(userId);
    }

    @Test
    void testRaiseBidWithNonexistentBid() {
        DisplayBidRequest request = new DisplayBidRequest();
        request.setBidId(99L);
        request.setNewBidAmount(100d);

        User user = createMockBidUser().getUser();

        when(bidService.findById(anyLong())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> auctionService.raiseBid(request, user));
        verify(bidService, times(1)).findById(anyLong());
    }


    @Test
    void testPlaceBidOnInactiveBid() {
        Long bidId = 1L;
        Bid bid = createMockBid();
        bid.set_active(false);

        when(bidService.findById(bidId)).thenReturn(bid);

        assertThrows(RuntimeException.class, () -> auctionService.placeBid(bidId));
        verify(bidService, times(1)).findById(bidId);
    }




    // Helper method to create a mock Bid object
    private Bid createMockBid() {
        Bid bid = new Bid();
        bid.setId(1L);
        bid.setDate(LocalDateTime.now());
        return bid;
    }

    // Helper method to create a mock BidUser object
    private BidUser createMockBidUser() {
        BidUser bidUser = new BidUser();
        bidUser.setId(1L);
        User user = new User();
        user.setId(1);
        bidUser.setUser(user);
        return bidUser;
    }

    @Test
    void testDiscardActiveStatus() {

        List<BidUser> mockBidUsers = new ArrayList<>();
        BidUser activeBidUser = new BidUser();
        activeBidUser.setId(1L);
        activeBidUser.setIs_Active(true);

        Bid activeBid = new Bid();
        activeBid.setId(1L);
        activeBid.set_active(false);
        activeBidUser.setBid(activeBid);
        mockBidUsers.add(activeBidUser);

        when(bidUserService.findByActive(true)).thenReturn(mockBidUsers);
        when(bidService.findById(anyLong())).thenReturn(activeBid);

        auctionService.discardActiveStatus();

        verify(bidUserService, times(1)).findByActive(true);
        verify(bidUserService, times(1)).save(activeBidUser);
        assertFalse(activeBidUser.getIs_Active());
    }
}


