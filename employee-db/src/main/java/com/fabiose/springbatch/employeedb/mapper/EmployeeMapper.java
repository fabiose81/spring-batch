package com.fabiose.springbatch.employeedb.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.fabiose.springbatch.employeedb.domain.Employee;

public class EmployeeMapper implements FieldSetMapper<Employee>{

	@Override
	public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
		
		Employee employee = new Employee();
		employee.setName(fieldSet.readString("name"));
		employee.setProject(fieldSet.readString("project"));
		employee.setStart(fieldSet.readDate("start", "yyyy-MM-dd"));
		employee.setEnd(fieldSet.readDate("end", "yyyy-MM-dd"));
		
		return employee;
	}

}
