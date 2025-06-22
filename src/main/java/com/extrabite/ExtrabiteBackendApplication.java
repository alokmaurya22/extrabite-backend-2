package com.extrabite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExtrabiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtrabiteBackendApplication.class, args);
	}
}