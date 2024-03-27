package com.group1.ecocredit;

import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.PickupStatus;
import com.group1.ecocredit.models.Category;
import com.group1.ecocredit.models.Status;
import com.group1.ecocredit.models.*;
import com.group1.ecocredit.repositories.CategoryPriceRepository;
import com.group1.ecocredit.services.CategoryPriceService;
import com.group1.ecocredit.services.CategoryService;
import com.group1.ecocredit.services.StatusService;
import com.group1.ecocredit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class EcocreditApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private CategoryPriceService categoryPriceService;


    public static void main(String[] args) {

        SpringApplication.run(EcocreditApplication.class, args);
    }

    @Override
    public void run(String... args) {
        registerAdmin();

        var categories = getWasteCategories();
        prefillWasteCategoryLookup(categories);

        var statuses = getPickupStatuses();
        prefillPickupStatusLookup(statuses);

        prefillCategoryPrice();
    }

    private void registerAdmin() {
        var adminAccount = userService.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            var admin = new User();
            admin.setEmail("ecocredit.donotreply@gmail.com");
            admin.setFirstName("Admin");
            admin.setLastName("EcoCredit");
            admin.setRole(Role.ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode(
                    "adminpassword"));
            admin.setEnabled(true);
            userService.save(admin);
        }
    }

    private List<Category> getWasteCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "electronics"));
        categories.add(new Category(2, "paper"));
        categories.add(new Category(3, "biodegradable"));
        categories.add(new Category(4, "plastics"));
        categories.add(new Category(5, "glass"));
        categories.add(new Category(6, "mixed"));
        return categories;
    }


    private void prefillWasteCategoryLookup(List<Category> categories) {
        for (var category : categories) {

            Optional<Category> categoryOptional =
                    categoryService.findByValue(category.getValue());

            if (categoryOptional.isEmpty()) {
                categoryService.save(new Category(category.getId(),
                        category.getValue()));
            }
        }
    }

    private List<Status> getPickupStatuses() {
        List<Status> statuses = new ArrayList<>();
        statuses.add(new Status(1, PickupStatus.SCHEDULED));
        statuses.add(new Status(2, PickupStatus.IN_PROGRESS));
        statuses.add(new Status(3, PickupStatus.COMPLETED));
        statuses.add(new Status(4, PickupStatus.CANCELED));
        statuses.add(new Status(5, PickupStatus.AWAITING_PAYMENT));
        return statuses;
    }



    private void prefillPickupStatusLookup(List<Status> statuses) {
        for (var status : statuses) {

            Optional<Status> statusOptional =
                    statusService.findById(status.getId());

            if (statusOptional.isEmpty()) {
                statusService.save(new Status(status.getId(),
                        status.getValue()));
            }
        }
    }


    private void prefillCategoryPrice() {

        List<Category> categories = getWasteCategories();
        float base = 1.0f;
        float multiplier = 1.2f;
        for (Category category : categories) {
            base = base * multiplier;


            CategoryPrice categoryPriceDB = categoryPriceService.findByCategoryName(category.getValue());

            if (categoryPriceDB == null) {

                CategoryPrice categoryPrice = new CategoryPrice();

                categoryPrice.setCategory(category);
                categoryPrice.setValue(base);
                categoryPriceService.save(categoryPrice);
            }

        }
    }
}
