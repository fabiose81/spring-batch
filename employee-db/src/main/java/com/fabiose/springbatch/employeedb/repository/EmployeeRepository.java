package com.fabiose.springbatch.employeedb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabiose.springbatch.employeedb.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
