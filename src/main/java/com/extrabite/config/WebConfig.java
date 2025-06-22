package com.extrabite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all routes
                .allowedOrigins("*") // Allow all domains
                .allowedMethods("*") // Allow GET, POST, PUT, DELETE etc.
                .allowedHeaders("*"); // Allow all headers like Content-Type, Authorization
    }
}