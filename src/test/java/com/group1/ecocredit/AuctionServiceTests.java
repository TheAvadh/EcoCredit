package com.group1.ecocredit;

import com.group1.ecocredit.models.Bid;
import com.group1.ecocredit.services.AuctionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
public class AuctionServiceTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuctionService auctionService;

    @Test
    public void viewAllActiveBids_ShouldReturnBids() throws Exception {
        given(auctionService.viewAllActiveBids()).willReturn(List.of(new Bid(), new Bid()));

        mockMvc.perform(get("/recycler/activeBids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    public void viewUserBids_ShouldReturnUserBids() throws Exception {
        given(auctionService.viewUserBids(anyInt())).willReturn(List.of(new Bid()));

        mockMvc.perform(get("/recycler/userBids/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }
    @Test
    public void placeOrUpdateBid_ShouldReturnUpdatedBid() throws Exception {
        Bid updatedBid = new Bid();
        updatedBid.setTop_bid_amount(500);
        given(auctionService.placeOrUpdateBid(anyInt(), anyLong(), anyInt())).willReturn(updatedBid);

        mockMvc.perform(post("/recycler/placeOrUpdateBid")
                        .param("userId", "1")
                        .param("bidId", "1")
                        .param("newBidAmount", "500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.top_bid_amount", is(500)));
    }

    @Test
    public void incrementBid_ShouldReturnIncrementedBid() throws Exception {
        Bid incrementedBid = new Bid();
        incrementedBid.setTop_bid_amount(300);
        given(auctionService.incrementBid(anyLong())).willReturn(incrementedBid);

        mockMvc.perform(put("/recycler/incrementBid/{bidId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.top_bid_amount", is(300)));
    }
}
