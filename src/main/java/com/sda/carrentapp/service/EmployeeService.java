package com.sda.carrentapp.service;

import com.sda.carrentapp.entity.User;
import com.sda.carrentapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;


    public List<User> getEmployees() {
        return employeeRepository.getAllEmployees();
    }
//
//    public Employee getEmployeeByID(Long id) {
//        return employeeRepository.getOne(id);
//    }
//
//    public void delete(Long id) {
//        List<Employee> employees = getEmployees();
//
//
//        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
//        employee.setEntityStatus(EntityStatus.ARCHIVED);
//        employeeRepository.save(employee);
//    }
//
//    public void saveEmployee(Employee employee) {
//        employee.setEntityStatus(EntityStatus.ACTIVE);
//        employeeRepository.save(employee);
//    }


}
