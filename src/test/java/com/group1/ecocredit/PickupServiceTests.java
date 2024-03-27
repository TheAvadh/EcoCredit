package com.group1.ecocredit;

import com.group1.ecocredit.dto.PickupActionRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.dto.PickupStatusResponse;
import com.group1.ecocredit.dto.PickupWaste;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.*;
import com.group1.ecocredit.services.*;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.implementations.PickupServiceImpl;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    private static WalletService walletService;
    private static PriceMapperService priceMapperService;
    private static CreditConversionService creditConversionService;
    private static WasteServiceCustomer wasteServiceCustomer;

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
        walletService = Mockito.mock(WalletService.class);
        priceMapperService = Mockito.mock(PriceMapperService.class);
        creditConversionService = Mockito.mock(CreditConversionService.class);
        wasteServiceCustomer = Mockito.mock(WasteServiceCustomer.class);
        pickupService = Mockito.mock(PickupService.class);


        pickupService = new PickupServiceImpl(pickupRepository,
                wasteRepository, statusRepository, categoryRepository,
                pickupPaymentActionService, walletService, priceMapperService,
                creditConversionService, wasteServiceCustomer);
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
        PickupActionRequest pickupToCancel = new PickupActionRequest(PICKUP_ID);
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
        PickupActionRequest pickupToCancel = new PickupActionRequest(PICKUP_ID);
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
        PickupActionRequest pickupToCancel = new PickupActionRequest(PICKUP_ID);
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

    @Test
    void getPickupStatus_WhenUserHasPickups_ShouldReturnStatusList() {
        // Arrange
        Long userId = 1L;
        List<Pickup> pickups = List.of(
                new Pickup(1L, LocalDateTime.now(), new User(), new Status(1, "AWAITING_PAYMENT"), "paymentId1"),
                new Pickup(2L, LocalDateTime.now(), new User(), new Status(2, "COMPLETED"), "paymentId2")
        );
        when(pickupRepository.findByUserId(userId)).thenReturn(pickups);

        // Act
        List<PickupStatusResponse> statusResponses = pickupService.getPickupStatus(userId);

        // Assert
        assertEquals(pickups.size(), statusResponses.size());
        verify(pickupRepository).findByUserId(userId);
    }

    @Test
    void getPickupStatus_WhenUserHasNoPickups_ShouldThrowException() {
        // Arrange
        Long userId = 1L;
        when(pickupRepository.findByUserId(userId)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> pickupService.getPickupStatus(userId));
    }

    @Test
    void confirmPickup_WhenPaymentIsDone_ShouldUpdateStatus() throws StripeException {
        // Arrange
        Long pickupId = 1L;
        Pickup pickup = new Pickup(pickupId, LocalDateTime.now(), new User(), new Status(1, "AWAITING_PAYMENT"), "paymentId");
        when(pickupRepository.findById(pickupId)).thenReturn(Optional.of(pickup));
        when(pickupPaymentActionService.isPaymentDone("paymentId")).thenReturn(true);
        Status scheduledStatus = new Status(2, "SCHEDULED");
        when(statusRepository.findByValue(PickupStatus.SCHEDULED)).thenReturn(Optional.of(scheduledStatus));

        // Act
        pickupService.confirmPickup(pickupId);

        // Assert
        assertEquals("SCHEDULED", pickup.getStatus().getValue());
        verify(pickupRepository).save(pickup);
    }

    @Test
    void confirmPickup_WhenPickupNotFound_ShouldNotThrowException() {
        // Arrange
        Long pickupId = 1L;
        when(pickupRepository.findById(pickupId)).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> pickupService.confirmPickup(pickupId));
    }

    @Test
    void addSessionIdToPickup_WhenPickupExists_ShouldUpdatePaymentId() {
        // Arrange
        Long pickupId = 1L;
        String sessionId = "newSessionId";
        Pickup pickup = new Pickup(pickupId, LocalDateTime.now(), new User(), new Status(1, "AWAITING_PAYMENT"), "oldSessionId");
        when(pickupRepository.findById(pickupId)).thenReturn(Optional.of(pickup));

        // Act
        pickupService.addSessionIdToPickup(pickupId, sessionId);

        // Assert
        assertEquals(sessionId, pickup.getPaymentId());
        verify(pickupRepository).save(pickup);
    }
}