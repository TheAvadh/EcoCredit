package com.group1.ecocredit.models;

public final class PickupStatus {
    private PickupStatus() {
        // To prevent anyone from constructing objects of this class
    }
    public static String SCHEDULED = "SCHEDULED";
    public static String IN_PROGRESS = "IN_PROGRESS";
    public static String COMPLETED = "COMPLETED";
    public static String CANCELED = "CANCELED";
    public static String AWAITING_PAYMENT = "AWAITING_PAYMENT";
}
