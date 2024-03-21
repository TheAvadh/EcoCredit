package com.group1.ecocredit;

import com.group1.ecocredit.models.admin.PickupQueryResult;
import com.group1.ecocredit.repositories.PickupRepository;
import com.group1.ecocredit.services.admin.implementations.PickupAdminServiceImpl;
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
    private PickupRepository pickupRepository;
    @InjectMocks
    private PickupAdminServiceImpl pickupAdminService;
    private PickupQueryResult dbPickupScheduledFirst;
    private PickupQueryResult dbPickupScheduledSecond;
    private List<PickupQueryResult> dbPickupsScheduled;
    private PickupQueryResult dbPickupCompletedFirst;
    private PickupQueryResult dbPickupCompletedSecond;
    private List<PickupQueryResult> dbPickupsCompleted;

    @BeforeEach
    void setup() {
        // Arrange scheduled pickup with two waste items from DB
        dbPickupsScheduled = new ArrayList<>();

        dbPickupScheduledFirst = new PickupQueryResult();
        dbPickupScheduledFirst.setId(1L);
        dbPickupScheduledFirst.setStatus("SCHEDULED");
        dbPickupScheduledFirst.setCategory("paper");
        dbPickupScheduledFirst.setWasteId(1L);
        dbPickupScheduledFirst.setWeight(1F);
        dbPickupScheduledFirst.setDateTime(LocalDateTime.now());

        dbPickupsScheduled.add(dbPickupScheduledFirst);

        dbPickupScheduledSecond = new PickupQueryResult();
        dbPickupScheduledSecond.setId(1L);
        dbPickupScheduledSecond.setStatus("SCHEDULED");
        dbPickupScheduledSecond.setCategory("biodegradable");
        dbPickupScheduledSecond.setWasteId(2L);
        dbPickupScheduledSecond.setWeight(1F);
        dbPickupScheduledSecond.setDateTime(LocalDateTime.now());

        dbPickupsScheduled.add(dbPickupScheduledSecond);

        // Arrange completed pickup with two waste items from DB
        dbPickupsCompleted = new ArrayList<>();

        dbPickupCompletedFirst = new PickupQueryResult();
        dbPickupCompletedFirst.setId(1L);
        dbPickupCompletedFirst.setStatus("COMPLETED");
        dbPickupCompletedFirst.setCategory("paper");
        dbPickupCompletedFirst.setWasteId(1L);
        dbPickupCompletedFirst.setWeight(1F);
        dbPickupCompletedFirst.setDateTime(LocalDateTime.now());

        dbPickupsCompleted.add(dbPickupCompletedFirst);

        dbPickupCompletedSecond = new PickupQueryResult();
        dbPickupCompletedSecond.setId(1L);
        dbPickupCompletedSecond.setStatus("COMPLETED");
        dbPickupCompletedSecond.setCategory("biodegradable");
        dbPickupCompletedSecond.setWasteId(2L);
        dbPickupCompletedSecond.setWeight(1F);
        dbPickupCompletedSecond.setDateTime(LocalDateTime.now());

        dbPickupsCompleted.add(dbPickupCompletedSecond);
    }


    @Test
    void testGetScheduledPickupsSuccess() throws SQLException {
        Mockito.when(pickupRepository.findScheduledPickups()).thenReturn(dbPickupsScheduled);

        var pickups = pickupAdminService.getScheduledPickups();
        var firstPickup = pickups.getFirst();

        assertEquals("pickup lists should have same size", 1, pickups.size());

        assertEquals("pickup should have same id as db pickup",
                dbPickupScheduledFirst.getId(), firstPickup.getId());
        assertEquals("pickup should have same date as db pickup",
                dbPickupScheduledFirst.getDateTime().toLocalDate().toString(),
                firstPickup.getDate());
        assertEquals("pickup should have same time as db pickup",
                dbPickupScheduledFirst.getDateTime().toLocalTime().toString(),
                firstPickup.getTime());
        assertEquals("pickups should have same user id as db pickup",
                dbPickupScheduledFirst.getUserId(), firstPickup.getUserId());
        assertEquals("pickups should have same status as db pickup",
                dbPickupScheduledFirst.getStatus(), firstPickup.getStatus());

        assertEquals("pickup should have same waste count as db pickup", 2,
                firstPickup.getWastes().size());

        assertEquals("first waste should have same id as db waste",
                dbPickupScheduledFirst.getWasteId(),
                firstPickup.getWastes().getFirst().getWasteId());
        assertEquals("first waste should have same weight as db waste",
                dbPickupScheduledFirst.getWeight(),
                firstPickup.getWastes().getFirst().getWeight());
        assertEquals("first waste should have same category as db waste",
                dbPickupScheduledFirst.getCategory(),
                firstPickup.getWastes().getFirst().getCategory());

        assertEquals("second waste should have same id as db waste",
                dbPickupScheduledSecond.getWasteId(),
                firstPickup.getWastes().getLast().getWasteId());
        assertEquals("second waste should have same weight as db waste",
                dbPickupScheduledSecond.getWeight(),
                firstPickup.getWastes().getLast().getWeight());
        assertEquals("second waste should have same category as db waste",
                dbPickupScheduledSecond.getCategory(),
                firstPickup.getWastes().getLast().getCategory());
    }

    @Test
    void testGetCompletedPickupsSuccess() throws SQLException {
        Mockito.when(pickupRepository.findCompletedPickups()).thenReturn(dbPickupsCompleted);

        var pickups = pickupAdminService.getCompletedPickups();
        var firstPickup = pickups.getFirst();

        assertEquals("pickup lists should have same size", 1, pickups.size());

        assertEquals("pickup should have same id as db pickup",
                dbPickupCompletedFirst.getId(), firstPickup.getId());
        assertEquals("pickup should have same date as db pickup",
                dbPickupCompletedFirst.getDateTime().toLocalDate().toString(),
                firstPickup.getDate());
        assertEquals("pickup should have same time as db pickup",
                dbPickupCompletedFirst.getDateTime().toLocalTime().toString(),
                firstPickup.getTime());
        assertEquals("pickups should have same user id as db pickup",
                dbPickupCompletedFirst.getUserId(), firstPickup.getUserId());
        assertEquals("pickups should have same status as db pickup",
                dbPickupCompletedFirst.getStatus(), firstPickup.getStatus());

        assertEquals("pickup should have same waste count as db pickup", 2,
                firstPickup.getWastes().size());

        assertEquals("first waste should have same id as db waste",
                dbPickupCompletedFirst.getWasteId(),
                firstPickup.getWastes().getFirst().getWasteId());
        assertEquals("first waste should have same weight as db waste",
                dbPickupCompletedFirst.getWeight(),
                firstPickup.getWastes().getFirst().getWeight());
        assertEquals("first waste should have same category as db waste",
                dbPickupCompletedFirst.getCategory(),
                firstPickup.getWastes().getFirst().getCategory());

        assertEquals("second waste should have same id as db waste",
                dbPickupCompletedSecond.getWasteId(),
                firstPickup.getWastes().getLast().getWasteId());
        assertEquals("second waste should have same weight as db waste",
                dbPickupCompletedSecond.getWeight(),
                firstPickup.getWastes().getLast().getWeight());
        assertEquals("second waste should have same category as db waste",
                dbPickupCompletedSecond.getCategory(),
                firstPickup.getWastes().getLast().getCategory());
    }

    @Test
    void testGetEmptyPickupsFromDb() throws SQLException {
        Mockito.when(pickupRepository.findScheduledPickups()).thenReturn(new ArrayList<>());

        var pickups = pickupAdminService.getScheduledPickups();

        assertEquals("pickup size should be 0", 0, pickups.size());
    }

    @Test
    void testGetNullPickupsFromDb() throws SQLException {
        Mockito.when(pickupRepository.findScheduledPickups()).thenReturn(null);

        var pickups = pickupAdminService.getScheduledPickups();

        assertEquals("pickup size should be 0", 0, pickups.size());
    }

}