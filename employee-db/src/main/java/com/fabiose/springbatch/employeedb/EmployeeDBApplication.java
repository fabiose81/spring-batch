package com.fabiose.springbatch.employeedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeDBApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDBApplication.class, args);
	}
	
}
