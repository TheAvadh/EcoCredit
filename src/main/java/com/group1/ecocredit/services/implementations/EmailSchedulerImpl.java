package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.ConfirmationEmail;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.repositories.ConfirmationEmailRepository;
import com.group1.ecocredit.repositories.PickupRepository;
import com.group1.ecocredit.services.EmailScheduler;
import com.group1.ecocredit.services.EmailService;
import org.checkerframework.checker.units.qual.C;
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
    private PickupRepository pickupRepository; // TODO - service

    @Autowired
    private EmailService emailServices;

    @Autowired
    private ConfirmationEmailRepository confirmationEmailRepository; // TODO
    // - service

    @Value("${max.retries}")
    private int maxRetries;

    public EmailSchedulerImpl(PickupRepository pickupRepository, EmailService emailService) {
        this.pickupRepository = pickupRepository;
        this.emailServices = emailService;
    }


    @Override
    public void sendEmailToPickupsThatAreScheduled() {

        List<Pickup> pickupList = pickupRepository.findAllPickupsWithEmailsNotSent();

        for (Pickup pickup : pickupList) {
            try {
                emailServices.sendPickupScheduledEmail(pickup);
                ConfirmationEmail confirmationEmail = new ConfirmationEmail();
                confirmationEmail.setEmailSent(true);
                confirmationEmail.setPickup(pickup);
                confirmationEmail.setRetryCounter(confirmationEmail.getRetryCounter() + 1);
                confirmationEmailRepository.save(confirmationEmail);
            } catch (Exception e) {
                System.out.println("Error while sending email to user: " + pickup.getUser().getUsername());
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        sendEmailToPickupsThatAreScheduled();
    }
}
