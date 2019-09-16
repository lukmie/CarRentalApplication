package com.sda.carrentapp.controller;

import com.sda.carrentapp.entity.Car;
import com.sda.carrentapp.entity.CreateCarRequest;
import com.sda.carrentapp.entity.UpdateCarRequest;
import com.sda.carrentapp.entity.UserBooking;
import com.sda.carrentapp.service.CarManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarRentController {
    private CarManager carManager;
    private UserBooking userBooking;

    public CarRentController(CarManager carManager, UserBooking userBooking) {
        this.carManager = carManager;
        this.userBooking = userBooking;
    }

    @GetMapping
    public String getCars(Model model) {
        List<Car> cars = carManager.getCars();
        model.addAttribute("cars", cars);
        model.addAttribute("userBooking", userBooking);
        return "cars";
    }

    @GetMapping("/addRentDepartment")
    String carAddView() {
        return "addcar";
    }

    @GetMapping("/edit/{id}")
    public String editCarView(Model model, @PathVariable Long id) {
        Car existingCar = carManager.getCarById(id);
        model.addAttribute(existingCar);
        return "editcar";
    }

    @PostMapping("/edit/{carNum}")
    public RedirectView editCat(@PathVariable Long carNum, UpdateCarRequest car) {
        carManager.patchCar(carNum, car);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/cars");

        return redirectView;
    }

    @PostMapping
    public String createCar(CreateCarRequest carRequest) {
        carManager.saveCar(carRequest);
        return "redirect:/cars";
    }

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
