package com.group1.ecocredit;

import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.models.Category;
import com.group1.ecocredit.models.Status;
import com.group1.ecocredit.repositories.CategoryRepository;
import com.group1.ecocredit.repositories.StatusRepository;
import com.group1.ecocredit.services.EmailScheduler;
import com.group1.ecocredit.services.implementations.EmailSchedulerImpl;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class EcocreditApplication implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StatusRepository statusRepository;

	private static Scheduler scheduler;


    public static void main(String[] args) {

        SpringApplication.run(EcocreditApplication.class, args);
    }

    @Override
    public void run(String... args) {

        var categories = getWasteCategories();
        prefillWasteCategoryLookup(categories);

        var statuses = getPickupStatuses();
        prefillPickupStatusLookup(statuses);

		createEmailScheduler();
    }

    private void createEmailScheduler() {
        try {

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();


			scheduler = schedulerFactory.getScheduler();

            JobDetail jobDetail = JobBuilder.newJob(EmailScheduler.class)
					.withIdentity("emailjob")
					.build();

			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("emailtrigger")
					.startNow()
					.withSchedule(
							SimpleScheduleBuilder
									.simpleSchedule()
									.withIntervalInMinutes(1)
									.repeatForever()
					)
					.build();
			scheduler.start();
		} catch (Exception e) {

        }
    }

	private void stopEmailScheduler() {
		try{
			scheduler.shutdown();
		} catch (Exception e) {

		}
	}

    private List<Category> getWasteCategories() {
        return Arrays.asList(
                new Category(1, "electronics"),
                new Category(2, "paper"),
                new Category(3, "biodegradable"),
                new Category(4, "plastics"),
                new Category(5, "glass"),
                new Category(6, "mixed")
        );
    }

    private void prefillWasteCategoryLookup(List<Category> categories) {
        for (var category : categories) {

            Optional<Category> categoryOptional =
                    categoryRepository.findByValue(category.getValue());

            if (categoryOptional.isEmpty()) {
                categoryRepository.save(new Category(category.getId(),
                        category.getValue()));
            }
        }
    }

    private List<Status> getPickupStatuses() {
        return Arrays.asList(
                new Status(1, PickupStatus.SCHEDULED),
                new Status(2, PickupStatus.IN_PROGRESS),
                new Status(3, PickupStatus.COMPLETED),
                new Status(4, PickupStatus.CANCELED)
        );
    }

    private void prefillPickupStatusLookup(List<Status> statuses) {
        for (var status : statuses) {

            Optional<Status> statusOptional =
                    statusRepository.findById(status.getId());

            if (statusOptional.isEmpty()) {
                statusRepository.save(new Status(status.getId(),
                        status.getValue()));
            }
        }
    }
}
