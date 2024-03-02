package com.group1.ecocredit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickupRequest {
    private String dateTime;
    private List<PickupWaste> wastes;
}
