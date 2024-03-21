package com.group1.ecocredit;

import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.CategoryRepository;
import com.group1.ecocredit.repositories.StatusRepository;
import com.group1.ecocredit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class EcocreditApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private StatusRepository statusRepository;

	public static void main(String[] args) {

		SpringApplication.run(EcocreditApplication.class, args);
	}

	@Override
	public void run(String... args){
		registerAdmin();

		var categories = getWasteCategories();
		prefillWasteCategoryLookup(categories);

		var statuses = getPickupStatuses();
		prefillPickupStatusLookup(statuses);
	}

	private void registerAdmin() {
		var adminAccount = userRepository.findByRole(Role.ADMIN);
		if (adminAccount == null) {
			var admin = new User();
			admin.setEmail("ecocredit.donotreply@gmail.com");
			admin.setFirstName("Admin");
			admin.setLastName("EcoCredit");
			admin.setRole(Role.ADMIN);
			admin.setPassword(new BCryptPasswordEncoder().encode(
					"adminpassword"));
			admin.setEnabled(true);
			userRepository.save(admin);
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
