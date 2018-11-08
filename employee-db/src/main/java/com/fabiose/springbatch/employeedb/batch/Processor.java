package com.fabiose.springbatch.employeedb.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.fabiose.springbatch.employeedb.domain.Employee;


@Component
public class Processor implements ItemProcessor<Employee, Employee>{

	private static final Map<String, String> PROJECT_NAMES =
            new HashMap<>();
	
	
	public Processor() {
		PROJECT_NAMES.put("001", "Java");
		PROJECT_NAMES.put("002", ".NET");
    }
	
	@Override
	public Employee process(Employee employee) throws Exception {
		String projectId = employee.getProject();
		String projectName = PROJECT_NAMES.get(projectId);
		
		employee.setProject(projectName);
		employee.setTime(new Date());
		
		return employee;
	}

}
