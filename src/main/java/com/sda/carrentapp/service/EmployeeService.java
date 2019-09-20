package com.sda.carrentapp.service;

import com.sda.carrentapp.entity.EntityStatus;
import com.sda.carrentapp.entity.User;
import com.sda.carrentapp.entity.UserDTO;
import com.sda.carrentapp.entity.mapper.UserMapper;
import com.sda.carrentapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private BCryptPasswordEncoder encoder;


    public List<User> getEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public User getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Employee with this id doesn't exist."));
    }

//    public void delete(Long id) {
//        List<Employee> employees = getEmployees();
//
//
//        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
//        employee.setEntityStatus(EntityStatus.ARCHIVED);
//        employeeRepository.save(employee);
//    }

    public void saveEmployee(UserDTO employeeDto) {
        employeeDto.setPassword(encoder.encode(employeeDto.getPassword()));
        User employee = UserMapper.map(employeeDto);
        employee.setEntityStatus(EntityStatus.ACTIVE);
        employeeRepository.save(employee);
    }


}
