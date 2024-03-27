package com.group1.ecocredit;

import com.group1.ecocredit.dto.admin.WasteUpdateRequest;
import com.group1.ecocredit.models.Category;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Waste;
import com.group1.ecocredit.repositories.WasteRepository;
import com.group1.ecocredit.services.implementations.WasteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WasteServiceCustomerTests {
    @Mock
    WasteRepository wasteRepository;
    @InjectMocks
    WasteServiceImpl wasteService;
    Long wasteId;
    WasteUpdateRequest wasteUpdateRequest;
    Waste dbWaste;

    @BeforeEach
    void setUp() {
        wasteId = 1L;

        dbWaste = new Waste();
        dbWaste.setWeight(0F);
        dbWaste.setId(wasteId);
        dbWaste.setPickup(new Pickup());
        dbWaste.setCategory(new Category());

        wasteUpdateRequest = new WasteUpdateRequest();
        wasteUpdateRequest.setWeight(1F);
    }

    @Test
    void testUpdateWeightSuccess() {
        // Arrange
        when(wasteRepository.findById(anyLong())).thenReturn(Optional.of(dbWaste));

        // Act
        wasteService.updateWeight(wasteId, wasteUpdateRequest);

        // Assert
        verify(wasteRepository, times(1)).save(any(Waste.class));
    }

    @Test
    void testUpdateWeightNotFound() {
        // Arrange
        when(wasteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        wasteService.updateWeight(wasteId, wasteUpdateRequest);

        // Assert
        verify(wasteRepository, times(0)).save(any(Waste.class));
    }
}
