package com.group1.ecocredit.services;

import org.quartz.Job;

public interface EmailScheduler extends Job {


    void sendEmailToPickupsThatAreScheduled();

}
