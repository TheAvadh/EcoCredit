package com.group1.ecocredit.models.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PickupQueryResult {
    private Long id;
    private LocalDateTime dateTime;
    private String userFirstName;
    private String userLastName;
    private Long wasteId;
    private Float weight;
    private String category;
    private String status;

    public PickupQueryResult(Long id,
                             LocalDateTime dateTime,
                             String userFirstName,
                             String userLastName,
                             Long wasteId,
                             Float weight,
                             String category,
                             String status) {
        this.id = id;
        this.dateTime = dateTime;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.wasteId = wasteId;
        this.weight = weight;
        this.category = category;
        this.status = status;
    }
}
