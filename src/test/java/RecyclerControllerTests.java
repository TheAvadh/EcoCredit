import com.group1.ecocredit.controllers.RecyclerController;
import com.group1.ecocredit.models.BidUser;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.AuctionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecyclerControllerTests {

    @Mock
    private AuctionService auctionService;

    @InjectMocks
    private RecyclerController recyclerController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserBids_Success() {

        User user = new User();
        user.setId(1);
        BidUser bidUser = new BidUser();
        bidUser.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<BidUser> expectedBids = new ArrayList<>();
        expectedBids.add(new BidUser());
        when(auctionService.viewUserBids(1)).thenReturn(expectedBids);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Ensure the response status is 200 OK
        assertNotNull(responseEntity.getBody()); // Ensure that the response body is not null
        assertEquals(expectedBids, responseEntity.getBody()); // Ensure the body matches the expected list of bids
        verify(auctionService, times(1)).viewUserBids(1);
    }

    @Test
    public void testGetUserBids_NoBids() {
        User user = new User();
        user.setId(1);
        BidUser bidUser = new BidUser();
        bidUser.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<BidUser> expectedBids = new ArrayList<>();
        when(auctionService.viewUserBids(1)).thenReturn(expectedBids);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_Unauthenticated() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(auctionService, never()).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_IllegalException() {
        User user = new User();
        user.setId(1);
        BidUser bidUser = new BidUser();
        bidUser.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(auctionService.viewUserBids(anyInt())).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }

    @Test
    public void testGetUserBids_Exception() {
        User user = new User();
        user.setId(1);
        BidUser bidUser = new BidUser();
        bidUser.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(auctionService.viewUserBids(anyInt())).thenThrow(RuntimeException.class);

        ResponseEntity<?> responseEntity = recyclerController.getUserBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewUserBids(anyInt());
    }


        @Test
        public void testGetAllActiveBids_Authenticated() {
            User user = new User();

            Authentication authentication = mock(Authentication.class);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Mock service response
            List<BidUser> expectedBids = new ArrayList<>();
            expectedBids.add(new BidUser());
            when(auctionService.viewAllActiveBids()).thenReturn(expectedBids);

            // Call the method
            ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

            // Verify
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(expectedBids, responseEntity.getBody());
            verify(auctionService, times(1)).viewAllActiveBids();
        }

    @Test
    public void testGetAllActiveBids_Unauthenticated() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(auctionService, never()).viewAllActiveBids();
    }

    @Test
    public void testGetAllActiveBids_NoActiveBids() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(auctionService, times(1)).viewAllActiveBids();
    }

    @Test
    public void testGetAllActiveBids_IllegalArgumentException() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(auctionService.viewAllActiveBids()).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewAllActiveBids();
    }


    @Test
    public void testGetAllActiveBids_Exception() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(auctionService.viewAllActiveBids()).thenThrow(HttpClientErrorException.NotFound.class);

        ResponseEntity<?> responseEntity = recyclerController.getAllActiveBids();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(auctionService, times(1)).viewAllActiveBids();
    }

}