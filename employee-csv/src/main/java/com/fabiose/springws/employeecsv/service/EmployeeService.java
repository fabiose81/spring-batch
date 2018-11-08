package com.fabiose.springws.employeecsv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fabiose.springws.employeecsv.domain.Entry;
import com.techprimers.spring_boot_soap_example.EmployeeRequest;
 

@Service
public class EmployeeService {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	public void save(EmployeeRequest request) throws IOException, Exception {
		
		List<ServiceInstance> instances = discoveryClient.getInstances("utils-service");
		
		if (instances != null && instances.size() > 0) {
			ServiceInstance serviceInstance = instances.get(0);
			
			String url = serviceInstance.getUri().toString().concat("/file/save");
			RestTemplate restTemplate = new RestTemplate();
						
			final StringBuilder body = new StringBuilder();
			
			request.getEmployee().forEach(r->{			
				body.append(r.getName()
						.concat(",")
						.concat(r.getProject())
						.concat(",")
						.concat(r.getStart())
						.concat(",")
						.concat(r.getEnd())
						.concat("\r\n"));
			});
						
			Entry entry = new Entry("employee.csv","name,project,start,end\n",body.toString(),null);
			
			restTemplate.postForObject(url, entry, Object.class);
		}

	}
	
}
