package com.group1.ecocredit.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PickupRequest {
    private String dateTime;
    private List<PickupWaste> wastes;
}
