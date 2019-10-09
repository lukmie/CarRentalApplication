package com.sda.carrentapp.controller;

import com.sda.carrentapp.entity.Car;
import com.sda.carrentapp.entity.Status;
import com.sda.carrentapp.entity.UserBooking;
import com.sda.carrentapp.entity.dto.CarDto;
import com.sda.carrentapp.service.CarManager;
import com.sda.carrentapp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarManager carManager;
    private final UserBooking userBooking;
    private final DepartmentService departmentService;

    @GetMapping
    public String getCars(Model model) {
        List<Car> cars = carManager.getActiveCars();
        model.addAttribute("cars", cars);
        model.addAttribute("userBooking", userBooking);
        return "cars";
    }

    @GetMapping("/addCar")
    public String addCarView(Model model) {
        Car car = new Car();
        List<Status> statusList = Stream.of(Status.values()).collect(Collectors.toList());
        model.addAttribute("statuses", statusList);
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("car", car);
        return "car-form";
    }

    @PostMapping("/saveCar")
    public String saveCar(@ModelAttribute("car") CarDto carDto) {
        carManager.saveCar(carDto);
        return "redirect:/cars";
    }

    @GetMapping("/editCar/{id}")
    public String editCarView(@PathVariable Long id, Model model) {
        Car car = carManager.getCarById(id);

        model.addAttribute("statuses", Stream.of(Status.values()).collect(Collectors.toList()));
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("car", car);
        return "car-form";
    }

    @PostMapping("/deleteCar/{id}")
    public String deleteCar(@PathVariable("id") Long id) {
        carManager.deleteCar(id);
        return "redirect:/cars";
    }

}
