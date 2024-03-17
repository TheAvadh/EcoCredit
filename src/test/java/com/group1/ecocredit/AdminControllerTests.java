package com.group1.ecocredit;

import com.group1.ecocredit.dto.BidCreateRequest;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.BidService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidService bidService;

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void shouldReturnHiAdminWhenSayHelloIsCalled() throws Exception {
        mockMvc.perform(get("/api/v1/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hi admin"));
    }

    @Test
    public void putWasteForBidWithoutAuthenticationShouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/admin/putwasteforbid"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void putWasteForBidAsUserRoleShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/admin/putwasteforbid"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void putWasteForBidAsAdminRoleShouldSucceed() throws Exception {
        mockMvc.perform(post("/api/v1/admin/putwasteforbid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Assuming JSON content is valid for BidCreateRequest
                .andExpect(status().isOk());
        verify(bidService, times(1)).putWasteForBid(any(BidCreateRequest.class));
    }

    @Test
    public void getAllActiveBidsWithoutAuthenticationShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/admin/allactivebids"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getAllActiveBidsAsUserRoleShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/admin/allactivebids"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllActiveBidsAsAdminRoleShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/v1/admin/allactivebids"))
                .andExpect(status().isOk());
        verify(bidService, times(1)).getAllActiveBids();
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void accessingAllBidsAsUserRoleShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/admin/allbids"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void putWasteForBidWithInvalidDataShouldReturnBadRequest() throws Exception {
        // Assume "{}" represents invalid data for BidCreateRequest
        mockMvc.perform(post("/api/v1/admin/putwasteforbid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
