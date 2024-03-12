package com.group1.ecocredit.models.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PickupQueryResult {
    private Integer id;
    private LocalDateTime dateTime;
    private Integer userId;
    private Long wasteId;
    private Float weight;
    private String category;
    private String status;
}
