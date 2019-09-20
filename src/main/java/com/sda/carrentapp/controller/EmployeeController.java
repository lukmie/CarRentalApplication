package com.sda.carrentapp.controller;

import com.sda.carrentapp.service.DepartmentService;
import com.sda.carrentapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;
    private DepartmentService departmentService;


    @GetMapping
    public String getEmployees(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        return "employees";
    }

//    @GetMapping("/addEmployee")
//    public String addEmployeeView(Model model) {
//        Employee employee = new Employee();
//        model.addAttribute("departments", departmentService.getDepartments());
//        model.addAttribute("employee", employee);
//        return "employee-form";
//    }
//
//    @PostMapping("/saveEmployee")
//    public String saveEmployee(@ModelAttribute("employee") EmployeeDTO employeeDTo) {
//        Employee employeeToSave = EmployeeMapper.map(employeeDTo);
//        employeeService.saveEmployee(employeeToSave);
////        return "redirect:/employees";
//        return "redirect:/departments/employees/" + employeeToSave.getDepartment().getId();
//    }
//
//    @GetMapping("/deleteEmployee")
//    public String deleteEmployee(@RequestParam("id") Long id, Model model) throws EmployeeNotFoundException {
//        Employee employeeByID = employeeService.getEmployeeByID(id);
//        List<Employee> employees = employeeService.getEmployees();
//        long count = employees.stream()
//                .filter(e -> e.getRole().equals(Role.MANAGER))
//                .filter(e -> e.getDepartment().getId().equals(employeeByID.getDepartment().getId()))
//                .filter(e -> e.getEntityStatus().equals(EntityStatus.ACTIVE))
//                .count();
//        Long departmentId = employeeByID.getDepartment().getId();
//        if (employeeByID.getRole().equals(Role.MANAGER) && count >= 2) {
//            employeeService.delete(id);
//            return "redirect:/departments/employees/" + departmentId;
//        } else {
//            model.addAttribute("departmentId", departmentId);
//            model.addAttribute("message",
//                    new Message("Warning", "Can't delete employee. You have to hire another manager to delete "
//                            + employeeByID.getFirstName() + " " + employeeByID.getLastName()));
//            return "message";
//        }
//
//    }
//
//    @GetMapping("/updateEmployee")
//    public String updateEmployee(@RequestParam("id") Long id, Model model) throws EmployeeNotFoundException {
//        model.addAttribute("employee", employeeService.getEmployeeByID(id));
//        return "employee-form";
//    }


}

