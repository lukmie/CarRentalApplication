package com.sda.carrentapp.controller;

import com.sda.carrentapp.entity.Car;
import com.sda.carrentapp.entity.UpdateCarRequest;
import com.sda.carrentapp.entity.UserBooking;
import com.sda.carrentapp.entity.dto.CarDto;
import com.sda.carrentapp.service.CarManager;
import com.sda.carrentapp.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@AllArgsConstructor

@Controller
@RequestMapping("/cars")
public class CarController {
    private CarManager carManager;
    private UserBooking userBooking;
    private DepartmentService departmentService;

    @GetMapping
    public String getCars(Model model) {
        List<Car> cars = carManager.getCars();
        model.addAttribute("cars", cars);
        model.addAttribute("userBooking", userBooking);
        return "cars";
    }

    @GetMapping("/addCar")
    public String addCarView(Model model) {
        Car car = new Car();
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("car", car);
        return "car-form";
    }

    @PostMapping("/saveCar")
    public String saveCar(@ModelAttribute("car") CarDto carDto) {
        System.out.println("**************************" + carDto);
        carManager.saveCar(carDto);
        return "redirect:/cars";
    }

    @GetMapping("/editCar/{id}")
    public String editCarView(@PathVariable Long id, Model model) {
        Car car = carManager.getCarById(id);
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("car", car);
        return "car-form";
    }


    @PostMapping("/edit/{carNum}")
    public RedirectView editCat(@PathVariable Long carNum, UpdateCarRequest car) {
        carManager.patchCar(carNum, car);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/cars");

        return redirectView;
    }

//    @PostMapping
//    public String createCar(CreateCarRequest carRequest) {
//        carManager.saveCar(carRequest);
//        return "redirect:/cars";
//    }

    @PostMapping("/delete/{carNum}")
    public String deleteCar(@PathVariable("carNum") Car car) {
        carManager.delete(car.getId());
        return "redirect:/cars";
    }

//    @PutMapping("/{id}")
//    public ResponseEntity updateCar(@PathVariable("id") Long id, @RequestBody UpdateCarRequest updateCarRequest) {
//        carManager.updateCar(id, updateCarRequest);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @PatchMapping("/{id}")
    public String patchCar(@PathVariable("id") Long id, UpdateCarRequest updateCarRequest) {
        carManager.patchCar(id, updateCarRequest);
        return "redirect:/cars";
    }
}
