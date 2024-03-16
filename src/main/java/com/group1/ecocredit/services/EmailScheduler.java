package com.group1.ecocredit.services;

import org.quartz.Job;
import org.springframework.scheduling.quartz.QuartzJobBean;


public interface EmailScheduler {
    void sendEmailToPickupsThatAreScheduled();

}
