package com.group1.ecocredit.QuartzTest;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzTestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Do some job logic here
        System.out.println("Executing Quartz job...");

        JobDetail jobDetail = context.getJobDetail();
    }

}
