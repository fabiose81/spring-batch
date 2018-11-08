package com.fabiose.springws.employeecsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeCsvApplication.class, args);
	}
}
