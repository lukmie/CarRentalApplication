package com.sda.carrentapp.controller;

import com.sda.carrentapp.service.CarManager;
import com.sda.carrentapp.service.DepartmentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private CarManager carManager;
    private DepartmentService departmentService;

    public HomeController(CarManager carManager, DepartmentService departmentService) {
        this.carManager = carManager;
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public String showHomeView(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(username)) {
            username = "";
        }
        model.addAttribute("username", username);
        return "main/index";
    }

    @GetMapping("/fleet")
    public String showFleetView(Model model) {
        model.addAttribute("cars", carManager.getCars());
        return "main/fleet-list";
    }

    @GetMapping("/branches")
    public String showBranchesView(Model model) {
        model.addAttribute("departments", departmentService.getDepartments());
        return "main/branches-list";
    }
}
