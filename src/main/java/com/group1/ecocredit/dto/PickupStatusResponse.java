package com.group1.ecocredit.dto;

import lombok.Data;

@Data
public class PickupStatusResponse {
    private Long pickupId;
    private String pickupStatus;
    private String pickupDate;
    private String pickupTime;

    public PickupStatusResponse(Long pickupId, String pickupStatus, String pickupDate, String pickupTime) {
        this.pickupId = pickupId;
        this.pickupStatus = pickupStatus;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
    }
}
