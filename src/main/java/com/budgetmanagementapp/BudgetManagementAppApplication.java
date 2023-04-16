package com.budgetmanagementapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.budgetmanagementapp")
public class BudgetManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetManagementAppApplication.class, args);
	}

}
