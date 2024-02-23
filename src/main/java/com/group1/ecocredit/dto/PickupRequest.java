package com.group1.ecocredit.dto;

import com.group1.ecocredit.models.Waste;

import java.time.LocalDateTime;
import java.util.List;

public class PickupRequest {
    private LocalDateTime dateTime;
    private List<Waste> wastes;

    private Double weight;
}
