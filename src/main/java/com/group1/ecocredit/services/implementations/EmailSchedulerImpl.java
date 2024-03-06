package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.repositories.ConfirmationEmailRepository;
import com.group1.ecocredit.repositories.PickupRepository;
import com.group1.ecocredit.services.EmailScheduler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmailSchedulerImpl implements EmailScheduler {

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private ConfirmationEmailRepository confirmationEmailRepository;

    @Override
    public void sendEmailToPickupsThatAreScheduled() {

        List<Pickup> pickupList = pickupRepository.findScheduledPickupsWithoutConfirmationEmailSent(PickupStatus.SCHEDULED);
        for (Pickup p : pickupList) {
            System.out.println(p.getId());
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        sendEmailToPickupsThatAreScheduled();
    }
}
