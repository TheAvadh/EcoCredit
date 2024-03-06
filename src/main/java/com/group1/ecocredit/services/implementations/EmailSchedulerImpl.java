package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.repositories.ConfirmationEmailRepository;
import com.group1.ecocredit.repositories.PickupRepository;
import com.group1.ecocredit.services.EmailScheduler;
import com.group1.ecocredit.services.EmailService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSchedulerImpl implements EmailScheduler, Job {

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private EmailService emailServices;

    public EmailSchedulerImpl(PickupRepository pickupRepository, EmailService emailService) {
        this.pickupRepository = pickupRepository;
        this.emailServices = emailService;
    }


    @Override
    public void sendEmailToPickupsThatAreScheduled() {
        
        List<Pickup> pickupList = pickupRepository.findScheduledPickupsWithoutConfirmationEmailSent(PickupStatus.SCHEDULED);

        for(Pickup pickup : pickupList) {
            try {
                emailServices.sendPickupScheduledEmail(pickup);
            } catch (Exception ignored) {

            }
        }
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        sendEmailToPickupsThatAreScheduled();

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        System.out.println(dataMap.getString("jobSays"));
    }
}
