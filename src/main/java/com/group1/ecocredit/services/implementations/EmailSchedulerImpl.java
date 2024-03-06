package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.models.ScheduledPickupsWithoutConfirmationEmailSent;
import com.group1.ecocredit.repositories.PickupRepository;
import com.group1.ecocredit.services.EmailScheduler;
import com.group1.ecocredit.services.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSchedulerImpl implements EmailScheduler, Job {

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private EmailService emailServices;

    @Value("${MAX_RETRIES}")
    private int maxRetries;

    public EmailSchedulerImpl(PickupRepository pickupRepository, EmailService emailService) {
        this.pickupRepository = pickupRepository;
        this.emailServices = emailService;
    }


    @Override
    public void sendEmailToPickupsThatAreScheduled() {

        List<ScheduledPickupsWithoutConfirmationEmailSent> pickupList = pickupRepository.findScheduledPickupsWithoutConfirmationEmailSent(PickupStatus.SCHEDULED);

        for(ScheduledPickupsWithoutConfirmationEmailSent pickup : pickupList) {
            try {
                if(pickup.getRetryCounter() < maxRetries) {

                    Pickup pickupObj = pickupRepository.findById(pickup.getPickupId()).get();
                    emailServices.sendPickupScheduledEmail(pickupObj);
                }
            } catch (Exception ignored) {

            }
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        sendEmailToPickupsThatAreScheduled();
    }
}
