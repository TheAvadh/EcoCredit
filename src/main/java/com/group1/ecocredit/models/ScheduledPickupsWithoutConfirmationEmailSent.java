package com.group1.ecocredit.models;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduledPickupsWithoutConfirmationEmailSent {

    private final Long pickupId;
    private final LocalDateTime pickupDateTime;
    private final boolean emailSent;
    private final int retryCounter;

    // Constructors, getters, and setters
    // Constructor should accept the relevant attributes from both entities

    public ScheduledPickupsWithoutConfirmationEmailSent(Long pickupId, LocalDateTime pickupDateTime, boolean emailSent, int retryCounter) {
        this.pickupId = pickupId;
        this.pickupDateTime = pickupDateTime;
        this.emailSent = emailSent;
        this.retryCounter = retryCounter;
    }

    // Getters and setters
}

