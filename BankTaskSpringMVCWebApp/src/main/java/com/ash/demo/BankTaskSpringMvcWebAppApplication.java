package com.ash.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//so the three steps are - sprint boot, entity scan and enable jpa repos
@SpringBootApplication(scanBasePackages = "com.ash")
@EntityScan(basePackages = "com.ash.entity")
@EnableJpaRepositories(basePackages = "com.ash.model.persistence")
public class BankTaskSpringMvcWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTaskSpringMvcWebAppApplication.class, args);
	}

}
