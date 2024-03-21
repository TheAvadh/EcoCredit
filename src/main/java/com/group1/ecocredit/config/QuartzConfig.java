package com.group1.ecocredit.config;

import com.group1.ecocredit.services.EmailScheduler;
import com.group1.ecocredit.services.implementations.EmailSchedulerImpl;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Value("${email.scheduler.interval.seconds}")
    int emailIntervalSeconds;

    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(EmailSchedulerImpl.class)
                .withIdentity("sampleJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger sampleJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(emailIntervalSeconds)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(sampleJobDetail())
                .withIdentity("sampleTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
