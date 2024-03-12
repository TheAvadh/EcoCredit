package com.group1.ecocredit;

import com.group1.ecocredit.models.admin.PickupQueryResult;
import com.group1.ecocredit.repositories.admin.PickupAdminRepository;
import com.group1.ecocredit.services.admin.PickupAdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PickupAdminServiceTests {
    @Mock
    private PickupAdminRepository pickupAdminRepository;
    @InjectMocks
    private PickupAdminServiceImpl pickupAdminService;
    private PickupQueryResult dbPickupFirst;
    private PickupQueryResult dbPickupSecond;
    private List<PickupQueryResult> dbPickups;

    @BeforeEach
    void setup() {
        // Arrange pickup with two waste items from DB
        dbPickups = new ArrayList<>();

        dbPickupFirst = new PickupQueryResult();
        dbPickupFirst.setId(1);
        dbPickupFirst.setStatus("SCHEDULED");
        dbPickupFirst.setCategory("paper");
        dbPickupFirst.setWasteId(1L);
        dbPickupFirst.setWeight(1F);
        dbPickupFirst.setDateTime(LocalDateTime.now());

        dbPickups.add(dbPickupFirst);

        dbPickupSecond = new PickupQueryResult();
        dbPickupSecond.setId(1);
        dbPickupSecond.setStatus("SCHEDULED");
        dbPickupSecond.setCategory("biodegradable");
        dbPickupSecond.setWasteId(2L);
        dbPickupSecond.setWeight(1F);
        dbPickupSecond.setDateTime(LocalDateTime.now());

        dbPickups.add(dbPickupSecond);
    }


    @Test
    void testGetPickupsSuccess() throws SQLException {
        // Arrange
        Mockito.when(pickupAdminRepository.findPickups()).thenReturn(dbPickups);

        // Act
        var pickups = pickupAdminService.getPickups();
        var firstPickup = pickups.getFirst();

        // Assert
        assertEquals("pickup lists should have same size", 1, pickups.size());

        assertEquals("pickup should have same id as db pickup",
                dbPickupFirst.getId(), firstPickup.getId());
        assertEquals("pickup should have same datetime as db pickup",
                dbPickupFirst.getDateTime(), firstPickup.getDateTime());
        assertEquals("pickups should have same user id as db pickup",
                dbPickupFirst.getUserId(), firstPickup.getUserId());
        assertEquals("pickups should have same status as db pickup",
                dbPickupFirst.getStatus(), firstPickup.getStatus());

        assertEquals("pickup should have same waste count as db pickup", 2,
                firstPickup.getWastes().size());

        assertEquals("first waste should have same id as db waste",
                dbPickupFirst.getWasteId(),
                firstPickup.getWastes().getFirst().getWasteId());
        assertEquals("first waste should have same weight as db waste",
                dbPickupFirst.getWeight(),
                firstPickup.getWastes().getFirst().getWeight());
        assertEquals("first waste should have same category as db waste",
                dbPickupFirst.getCategory(),
                firstPickup.getWastes().getFirst().getCategory());

        assertEquals("second waste should have same id as db waste",
                dbPickupSecond.getWasteId(),
                firstPickup.getWastes().getLast().getWasteId());
        assertEquals("second waste should have same weight as db waste",
                dbPickupSecond.getWeight(),
                firstPickup.getWastes().getLast().getWeight());
        assertEquals("second waste should have same category as db waste",
                dbPickupSecond.getCategory(),
                firstPickup.getWastes().getLast().getCategory());
    }

    @Test
    void testGetEmptyPickupsFromDb() throws SQLException {
        // Arrange
        Mockito.when(pickupAdminRepository.findPickups()).thenReturn(new ArrayList<>());

        // Act
        var pickups = pickupAdminService.getPickups();

        // Assert
        assertEquals("pickup size should be 0", 0, pickups.size());
    }

    @Test
    void testGetNullPickupsFromDb() throws SQLException {
        // Arrange
        Mockito.when(pickupAdminRepository.findPickups()).thenReturn(null);

        // Act
        var pickups = pickupAdminService.getPickups();

        // Assert
        assertEquals("pickup size should be 0", 0, pickups.size());
    }

}
