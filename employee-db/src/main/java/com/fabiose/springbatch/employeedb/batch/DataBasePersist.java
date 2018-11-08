package com.fabiose.springbatch.employeedb.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fabiose.springbatch.employeedb.domain.Employee;
import com.fabiose.springbatch.employeedb.domain.Entry;
import com.fabiose.springbatch.employeedb.repository.EmployeeRepository;


@Component
public class DataBasePersist implements ItemWriter<Employee>{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		employeeRepository.saveAll(employees);	
		
		List<ServiceInstance> instances = discoveryClient.getInstances("utils-service");
		
		if (instances != null && instances.size() > 0) {
			ServiceInstance serviceInstance = instances.get(0);
			
			String url = serviceInstance.getUri().toString().concat("/file/delete");
			RestTemplate restTemplate = new RestTemplate();
			
			Entry entry = new Entry("employee.csv",null,null,null);
			
			restTemplate.postForObject(url, entry, Object.class);
		}
	}

}
