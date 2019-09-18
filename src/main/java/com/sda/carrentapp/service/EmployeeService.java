package com.sda.carrentapp.service;

import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

//    private EmployeeRepository employeeRepository;
//
//    public EmployeeService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
//
//    public List<Employee> getEmployees() {
//        return employeeRepository.findAll();
//    }
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
