package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.ConfirmationEmail;
import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.repositories.ConfirmationEmailRepository;
import com.group1.ecocredit.services.EmailScheduler;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.PickupService;
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
    private PickupService pickupService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationEmailRepository confirmationEmailService;
    // - service

    @Value("${max.retries}")
    private int maxRetries;

    public EmailSchedulerImpl(PickupService pickupService,
                              EmailService emailService) {
        this.pickupService = pickupService;
        this.emailService = emailService;
    }


    @Override
    public void sendEmailToPickupsThatAreScheduled() {

        List<Pickup> pickupList = pickupService.findAllPickupsWithEmailsNotSent();

        for (Pickup pickup : pickupList) {
            try {
                emailService.sendPickupScheduledEmail(pickup);
                ConfirmationEmail confirmationEmail = new ConfirmationEmail();
                confirmationEmail.setEmailSent(true);
                confirmationEmail.setPickup(pickup);
                confirmationEmail.setRetryCounter(confirmationEmail.getRetryCounter() + 1);
                confirmationEmailService.save(confirmationEmail);
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
