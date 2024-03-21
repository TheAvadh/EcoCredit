package com.group1.ecocredit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Specify the endpoint for which CORS should be configured
                .allowedOrigins("http://localhost:3000") // Specify the origin(s) that are allowed to access the resource
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify the HTTP methods that are allowed
                .allowedHeaders("*"); // Specify the request headers that are allowed

    }
}
