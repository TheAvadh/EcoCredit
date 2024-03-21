package com.group1.ecocredit;

import com.group1.ecocredit.dto.PickupCancelRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.dto.PickupWaste;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.*;
import com.group1.ecocredit.services.PickupPaymentActionService;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.PickupService;
import com.group1.ecocredit.services.implementations.PickupServiceImpl;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PickupServiceTests {
    private static PickupRepository pickupRepository;
    private static WasteRepository wasteRepository;
    private static StatusRepository statusRepository;
    private static CategoryRepository categoryRepository;
    private static PickupService pickupService;
    private static TransactionRepository transactionRepository;

    private static PickupPaymentActionService pickupPaymentActionService;
    private static String PICKUP_DATE = "2024-05-01T15:00";
    private static String STATUS_SCHEDULED = "SCHEDULED";
    private static Integer STATUS_SCHEDULED_ID = 1;
    private static String STATUS_CANCELED = "CANCELED";
    private static Integer STATUS_CANCELED_ID = 4;
    private static Long PICKUP_ID = 1L;
    private static Integer CATEGORY_PLASTICS_ID = 4;
    private static String CATEGORY_PLASTICS = "plastics";
    private static Integer CATEGORY_BIODEGRADABLE_ID = 3;
    private static String CATEGORY_BIODEGRADABLE = "biodegradable";

    private static String PAYMENT_ID = "payment_id";

    @BeforeEach
    void setUp() {
        pickupRepository = Mockito.mock(PickupRepository.class);
        wasteRepository = Mockito.mock(WasteRepository.class);
        statusRepository = Mockito.mock(StatusRepository.class);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        pickupPaymentActionService = Mockito.mock(PickupPaymentActionService.class);
        pickupService = Mockito.mock(PickupService.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);



        pickupService = new PickupServiceImpl(pickupRepository,
                wasteRepository, statusRepository, categoryRepository, pickupPaymentActionService, transactionRepository);
    }

    @Test
    void testSchedulePickupSuccess() {
        // Arrange
        User user = new User();
        Status status = new Status(STATUS_SCHEDULED_ID, STATUS_SCHEDULED);

        // Pickup with one waste item
        List<PickupWaste> wastes = new ArrayList<>();
        wastes.add(new PickupWaste(CATEGORY_PLASTICS, 0.5f));
        PickupRequest pickupRequest = new PickupRequest(PICKUP_DATE, wastes);

        // Arrange DB items
        Pickup pickup = new Pickup(PICKUP_ID,
                LocalDateTime.parse(pickupRequest.getDateTime()), user, status, PAYMENT_ID);
        Category plasticsCategory = new Category(CATEGORY_PLASTICS_ID, CATEGORY_PLASTICS);

        Mockito.when(statusRepository.findByValue(PickupStatus.AWAITING_PAYMENT))
                .thenReturn(Optional.of(status));
        Mockito.when(pickupRepository.save(any(Pickup.class)))
                .thenReturn(pickup);
        Mockito.when(categoryRepository.findByValue(CATEGORY_PLASTICS))
                .thenReturn(Optional.of(plasticsCategory));

        // Act
        pickupService.schedulePickup(pickupRequest, user);

        // Verify that we saved a pickup and waste item
        verify(pickupRepository, times(1)).save(any(Pickup.class));
        verify(wasteRepository, times(1)).save(any(Waste.class));
    }

    @Test
    void testSchedulePickupWithMultipleWastesSuccess() {
        // Arrange
        User user = new User();
        Status status = new Status(STATUS_SCHEDULED_ID, STATUS_SCHEDULED);

        // Pickup with two waste items
        List<PickupWaste> wastes = new ArrayList<>();
        wastes.add(new PickupWaste(CATEGORY_PLASTICS, 0.5f));
        wastes.add(new PickupWaste(CATEGORY_BIODEGRADABLE, 1f));
        PickupRequest pickupRequest = new PickupRequest(PICKUP_DATE, wastes);

        // Arrange DB items
        Pickup pickup = new Pickup(PICKUP_ID,
                LocalDateTime.parse(pickupRequest.getDateTime()), user, status, PAYMENT_ID);
        Category plasticsCategory = new Category(CATEGORY_PLASTICS_ID,
                CATEGORY_PLASTICS);
        Category biodegradableCategory = new Category(CATEGORY_BIODEGRADABLE_ID,
                CATEGORY_BIODEGRADABLE);

        Mockito.when(statusRepository.findByValue(PickupStatus.AWAITING_PAYMENT))
                .thenReturn(Optional.of(status));
        Mockito.when(pickupRepository.save(any(Pickup.class)))
                .thenReturn(pickup);
        Mockito.when(categoryRepository.findByValue(CATEGORY_PLASTICS))
                .thenReturn(Optional.of(plasticsCategory));
        Mockito.when(categoryRepository.findByValue(CATEGORY_BIODEGRADABLE))
                .thenReturn(Optional.of(biodegradableCategory));

        // Act
        pickupService.schedulePickup(pickupRequest, user);

        // Verify that we saved a pickup
        verify(pickupRepository, times(1)).save(any(Pickup.class));
        // Verify that we saved two waste items
        verify(wasteRepository, times(2)).save(any(Waste.class));
    }

    @Test
    void testSchedulePickupWhenStatusNotFound() {
        // Arrange
        PickupRequest pickupRequest = new PickupRequest();
        User user = new User();
        Mockito.when(statusRepository.findByValue(PickupStatus.AWAITING_PAYMENT))
                .thenReturn(Optional.empty());

        // Act & Assert that an exception is thrown
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> pickupService.schedulePickup(pickupRequest, user)
        );
        String expectedMessage = "Status not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSchedulePickupWhenCategoryNotFound() {
        // Arrange
        List<PickupWaste> wastes = new ArrayList<>();
        wastes.add(new PickupWaste(null, 0f));
        PickupRequest pickupRequest = new PickupRequest(PICKUP_DATE, wastes);
        User user = new User();
        Status status = new Status(STATUS_SCHEDULED_ID, STATUS_SCHEDULED);
        Pickup pickup = new Pickup(PICKUP_ID,
                LocalDateTime.parse(pickupRequest.getDateTime()), user, status, "payment");

        Mockito.when(statusRepository.findByValue(PickupStatus.AWAITING_PAYMENT))
                .thenReturn(Optional.of(status));
        Mockito.when(pickupRepository.save(any(Pickup.class)))
                .thenReturn(pickup);
        Mockito.when(categoryRepository.findByValue(any()))
                .thenReturn(Optional.empty());

        // Act & Assert that exception is thrown
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> pickupService.schedulePickup(pickupRequest, user)
        );
        String expectedMessage = "Category not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCancelPickupSuccess() throws StripeException {
        // Arrange
        PickupCancelRequest pickupToCancel = new PickupCancelRequest(PICKUP_ID);
        User user = new User();
        Status status = new Status(STATUS_CANCELED_ID, STATUS_CANCELED);
        Pickup pickup = new Pickup(PICKUP_ID,
                LocalDateTime.parse(PICKUP_DATE), user, status, PAYMENT_ID);

        Mockito.when(pickupRepository.findById(pickupToCancel.getId()))
                .thenReturn(Optional.of(pickup));
        Mockito.when(statusRepository.findByValue(PickupStatus.CANCELED))
                .thenReturn(Optional.of(status));

        // Act
        boolean result = pickupService.cancelPickup(pickupToCancel);

        // Verify the method's return value
        assertTrue(result);

        // Verify that the pickup sets its status to canceled
        ArgumentCaptor<Pickup> pickupCaptor =
                ArgumentCaptor.forClass(Pickup.class);
        verify(pickupRepository, times(1)).save(pickupCaptor.capture());
        Pickup pickupCaptorValue = pickupCaptor.getValue();
        assertEquals(STATUS_CANCELED_ID, pickupCaptorValue.getStatus().getId());
        assertEquals(STATUS_CANCELED, pickupCaptorValue.getStatus().getValue());
    }


    @Test
    void testCancelPickupWhenPickupNotFound() throws StripeException {
        // Arrange
        PickupCancelRequest pickupToCancel = new PickupCancelRequest(PICKUP_ID);
        Mockito.when(pickupRepository.findById(pickupToCancel.getId()))
                .thenReturn(Optional.empty());

        // Act
        boolean result = pickupService.cancelPickup(pickupToCancel);

        // Verify the method's return value
        assertFalse(result);

        // Verify that the right methods are called
        verify(pickupRepository, times(1)).findById(anyLong());
        verify(statusRepository, never()).findByValue(anyString());
        verify(pickupRepository, never()).save(any(Pickup.class));
    }

    @Test
    void testCancelPickupWhenStatusNotFound() throws StripeException {
        // Arrange
        PickupCancelRequest pickupToCancel = new PickupCancelRequest(PICKUP_ID);
        User user = new User();
        Status status = new Status(STATUS_CANCELED_ID, STATUS_CANCELED);
        Pickup pickup = new Pickup(PICKUP_ID,
                LocalDateTime.parse(PICKUP_DATE), user, status, PAYMENT_ID);

        Mockito.when(pickupRepository.findById(pickupToCancel.getId()))
                .thenReturn(Optional.of(pickup));
        Mockito.when(statusRepository.findByValue(PickupStatus.CANCELED))
                .thenReturn(Optional.empty());

        // Act
        boolean result = pickupService.cancelPickup(pickupToCancel);

        // Verify the method's return value
        assertFalse(result);

        // Verify that the right methods are called
        verify(pickupRepository, times(1)).findById(anyLong());
        verify(statusRepository, times(1)).findByValue(anyString());
        verify(pickupRepository, never()).save(any(Pickup.class));
    }
}
