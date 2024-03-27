package com.group1.ecocredit;

import com.group1.ecocredit.repositories.BidRepository;
import com.group1.ecocredit.services.CategoryPriceService;
import com.group1.ecocredit.services.WasteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.group1.ecocredit.models.*;
import com.group1.ecocredit.services.implementations.BidServiceImpl;
import com.group1.ecocredit.dto.BidCreateRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class BidServiceTests {
    @Mock
    private BidRepository bidRepository;

    @Mock
    private WasteService wasteService;

    @Mock
    private CategoryPriceService categoryPriceService;


    @InjectMocks
    private BidServiceImpl bidService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenWasteIdDoesNotExist_thenThrowException() {
        BidCreateRequest request = new BidCreateRequest("2023-04-01T12:00", 1L);

        when(wasteService.findById(request.getWasteId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            bidService.putWasteForBid(request);
        });
    }


    @Test
    public void whenBidCreationDateIsInThePast_thenThrowException() {
        BidCreateRequest request = new BidCreateRequest(LocalDateTime.now().minusDays(1).toString(), 1L);
        Waste waste = new Waste();

        when(wasteService.findById(request.getWasteId())).thenReturn(Optional.of(waste));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bidService.putWasteForBid(request);
        });
    }



    @Test
    public void whenValidRequest_thenCreateBid() {
        BidCreateRequest request = new BidCreateRequest(LocalDateTime.now().plusDays(1).toString(), 1L);
        Waste waste = new Waste();
        waste.setId(1L);
        waste.setWeight(10f);
        Category category = new Category();
        category.setId(1);
        waste.setCategory(category);
        CategoryPrice categoryPrice = new CategoryPrice();
        categoryPrice.setValue(2f);

        when(wasteService.findById(request.getWasteId())).thenReturn(Optional.of(waste));
        when(categoryPriceService.findByCategoryId(waste.getCategory().getId())).thenReturn(Optional.of(categoryPrice));

        Bid result = bidService.putWasteForBid(request);

        assertNotNull(result);
        assertEquals(java.util.Optional.of(20.0), java.util.Optional.of(result.getBase_price()));
        verify(bidRepository).save(any(Bid.class));
    }


    @Test
    void testActivateBids_BidsToActivate() {

        LocalDateTime currentDateTime = LocalDateTime.now();

        Bid activeBid1 = new Bid();
        activeBid1.setId(1L);
        activeBid1.setDate(currentDateTime.minusHours(25));
        activeBid1.set_active(false);
        activeBid1.setSold(false);

        Bid activeBid2 = new Bid();
        activeBid2.setId(2L);
        activeBid2.setDate(currentDateTime.minusHours(23));
        activeBid2.set_active(false);
        activeBid2.setSold(false);

        List<Bid> bidsToActivate = List.of(activeBid1, activeBid2);
        when(bidRepository.findBidsToActivate()).thenReturn(bidsToActivate);


        bidService.activateBids();

        verify(bidRepository, times(2)).save(any());
        assert activeBid1.is_active();
        assert activeBid2.is_active();
    }

    @Test
    void testExpireBids_thenExpireActiveBids() {
        User user = new User();
        user.setId(5);

        Bid activeBid = new Bid();
        activeBid.setId(1L);
        activeBid.setDate(LocalDateTime.now().minusHours(25));
        activeBid.setUser(user);
        activeBid.set_active(true);

        when(bidRepository.findByIsActive(true)).thenReturn(Arrays.asList(activeBid));

        bidService.expireBids();

        assertFalse(activeBid.is_active());
        assertTrue(activeBid.isSold());
    }

    @Test
    void whenGetAllActiveBids_thenReturnAllActiveBids() {
        List<Bid> activeBids = new ArrayList<>();

        Bid activeBid1 = new Bid();
        activeBid1.setId(1L);
        activeBid1.set_active(true);
        activeBids.add(activeBid1);

        Bid activeBid2 = new Bid();
        activeBid2.setId(2L);
        activeBid2.set_active(true);
        activeBids.add(activeBid2);

        when(bidRepository.findByIsActive(true)).thenReturn(activeBids);

        List<Bid> result = bidService.getAllActiveBids();

        assertEquals(activeBids.size(), result.size());
        for (int i = 0; i < activeBids.size(); i++) {
            assertEquals(activeBids.get(i), result.get(i));
        }

        verify(bidRepository, times(1)).findByIsActive(true);

    }


    @Test
    void whenGetAllBids_thenReturnAllBids() {
        Bid bid1 = new Bid();
        Bid bid2 = new Bid();

        when(bidRepository.findAll()).thenReturn(Arrays.asList(bid1, bid2));

        List<Bid> result = bidService.getAllBids();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(bid1, bid2)));
    }
}
