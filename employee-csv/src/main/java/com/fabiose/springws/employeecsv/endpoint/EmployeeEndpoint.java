package com.fabiose.springws.employeecsv.endpoint;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fabiose.springws.employeecsv.service.EmployeeService;
import com.techprimers.spring_boot_soap_example.EmployeeRequest;

@Endpoint
public class EmployeeEndpoint {

	@Autowired
	private EmployeeService employeeService;
	
	@PayloadRoot(namespace = "http://techprimers.com/spring-boot-soap-example", localPart = "employeeRequest")
	@ResponsePayload
	public void employeeRequest(@RequestPayload EmployeeRequest request) throws IOException, Exception{
		employeeService.save(request);
	}
}
