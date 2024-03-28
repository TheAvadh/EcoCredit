package com.group1.ecocredit;

import com.group1.ecocredit.dto.PickupActionRequest;
import com.group1.ecocredit.dto.PickupRequest;
import com.group1.ecocredit.dto.PickupStatusResponse;
import com.group1.ecocredit.dto.PickupWaste;
import com.group1.ecocredit.enums.Currency;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.*;
import com.group1.ecocredit.services.*;
import com.group1.ecocredit.services.implementations.PickupServiceImpl;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PickupServiceTests {
    @Mock
    private static PickupRepository pickupRepository;
    @Mock
    private static WasteService wasteService;
    @Mock
    private static StatusService statusService;
    @Mock
    private static CategoryService categoryService;
    @InjectMocks
    private static PickupServiceImpl pickupService;
    @Mock
    private static WalletService walletService;
    @Mock
    private static PriceMapperService priceMapperService;
    @Mock
    private static CreditConversionService creditConversionService;
    @Mock
    private static WasteServiceCustomer wasteServiceCustomer;
    @Mock
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
    private Pickup pickup = new Pickup();



    @BeforeEach
    void setup() {
        User user = new User();
        user.setId(123);
        user.setEmail("email@email.com");

        pickup.setId(1L);
        pickup.setStatus(new Status());
        pickup.setUser(user);
        pickup.setDateTime(LocalDateTime.now());
        pickup.setPaymentId("payment_id");
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

        Mockito.when(statusService.findByValue(PickupStatus.AWAITING_PAYMENT))
                .thenReturn(Optional.of(status));
        Mockito.when(pickupRepository.save(any(Pickup.class)))
                .thenReturn(pickup);
        Mockito.when(categoryService.findByValue(CATEGORY_PLASTICS))
                .thenReturn(Optional.of(plasticsCategory));

        // Act
        pickupService.schedulePickup(pickupRequest, user);

        // Verify that we saved a pickup and waste item
        verify(pickupRepository, times(1)).save(any(Pickup.class));
        verify(wasteService, times(1)).save(any(Waste.class));
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

        Mockito.when(statusService.findByValue(PickupStatus.AWAITING_PAYMENT))
                .thenReturn(Optional.of(status));
        Mockito.when(pickupRepository.save(any(Pickup.class)))
                .thenReturn(pickup);
        Mockito.when(categoryService.findByValue(CATEGORY_PLASTICS))
                .thenReturn(Optional.of(plasticsCategory));
        Mockito.when(categoryService.findByValue(CATEGORY_BIODEGRADABLE))
                .thenReturn(Optional.of(biodegradableCategory));

        // Act
        pickupService.schedulePickup(pickupRequest, user);

        // Verify that we saved a pickup
        verify(pickupRepository, times(1)).save(any(Pickup.class));
        // Verify that we saved two waste items
        verify(wasteService, times(2)).save(any(Waste.class));
    }

    @Test
    void testSchedulePickupWhenStatusNotFound() {
        // Arrange
        PickupRequest pickupRequest = new PickupRequest();
        User user = new User();
        Mockito.when(statusService.findByValue(PickupStatus.AWAITING_PAYMENT))
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

        Mockito.when(statusService.findByValue(PickupStatus.AWAITING_PAYMENT))
                .thenReturn(Optional.of(status));
        Mockito.when(pickupRepository.save(any(Pickup.class)))
                .thenReturn(pickup);
        Mockito.when(categoryService.findByValue(any()))
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
        Mockito.when(statusService.findByValue(PickupStatus.CANCELED))
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
        verify(statusService, never()).findByValue(anyString());
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
        Mockito.when(statusService.findByValue(PickupStatus.CANCELED))
                .thenReturn(Optional.empty());

        // Act
        boolean result = pickupService.cancelPickup(pickupToCancel);

        // Verify the method's return value
        assertFalse(result);

        // Verify that the right methods are called
        verify(pickupRepository, times(1)).findById(anyLong());
        verify(statusService, times(1)).findByValue(anyString());
        verify(pickupRepository, never()).save(any(Pickup.class));
    }

    @Test
    void getPickupStatusFromUserID() {

        List<Pickup> pickups = new ArrayList<>();
        pickups.add(pickup);

        when(pickupRepository.findByUserId(1L)).thenReturn(pickups);

        List<PickupStatusResponse> pickupStatusResponses = pickupService.getPickupStatus(1L);

        assertEquals(1, pickupStatusResponses.size());
        verify(pickupRepository, times(1)).findByUserId(anyLong());
    }

    @Test
    void confirmPickupTest() throws StripeException {



        when(pickupRepository.findById(1L)).thenReturn(Optional.of(pickup));
        when(pickupPaymentActionService.isPaymentDone("payment_id")).thenReturn(true);

        Status status = new Status();
        status.setId(1);
        status.setValue(STATUS_SCHEDULED);

        when(statusService.findByValue(PickupStatus.SCHEDULED)).thenReturn(Optional.of(status));

        pickupService.confirmPickup(1L);

        verify(pickupRepository, times(1)).save(pickup);

    }

    @Test
    void addSessionToPickupIdTest() {
        when(pickupRepository.findById(1L)).thenReturn(Optional.of(pickup));

        pickupService.addSessionIdToPickup(1L, "session_id");

        verify(pickupRepository, times(1)).save(pickup);
    }

    @Test
    void completePickupTest() {
        Long pickupId = 1L;

        Waste waste1 = new Waste();
        waste1.setWeight(10.0f);
        waste1.setCategory(new Category());
        Waste waste2 = new Waste();
        waste2.setWeight(5.0f);
        waste2.setCategory(new Category());
        List<Waste> wastes = new ArrayList<>();
        wastes.add(waste1);
        wastes.add(waste2);

        when(pickupRepository.findById(pickupId)).thenReturn(Optional.of(pickup));
        when(wasteServiceCustomer.getAllWasteForPickup(pickupId)).thenReturn(wastes);
        when(priceMapperService.getPrice(null)).thenReturn(2.0f);
        when(priceMapperService.getPrice(null)).thenReturn(3.0f);
        Status status = new Status();
        status.setValue(PickupStatus.COMPLETED);

        when(statusService.findByValue(PickupStatus.COMPLETED)).thenReturn(Optional.of(status));

        doAnswer(invocation -> {
            double amount = invocation.getArgument(0);
            assertEquals(45.0, amount);
            return 45.0;
        }).when(creditConversionService).convert(anyDouble(), any(Currency.class));

        pickupService.completePickup(1L);

        assertEquals(PickupStatus.COMPLETED, pickup.getStatus().getValue());
        verify(pickupRepository, times(1)).save(pickup);
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
        when(statusService.findByValue(PickupStatus.SCHEDULED)).thenReturn(Optional.of(scheduledStatus));

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