package com.group1.ecocredit;

import com.group1.ecocredit.controllers.AdminController;
import com.group1.ecocredit.services.BidService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AdminController.class)
@ActiveProfiles("test")

public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidService bidService;


    @BeforeEach
    void setUp() {

        };

    }



