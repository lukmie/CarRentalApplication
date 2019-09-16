package com.sda.carrentapp.controller;

import com.sda.carrentapp.common.Message;
import com.sda.carrentapp.entity.Car;
import com.sda.carrentapp.entity.Department;
import com.sda.carrentapp.entity.UserBooking;
import com.sda.carrentapp.exception.BookingNotFoundException;
import com.sda.carrentapp.exception.RentStartDateIsNullException;
import com.sda.carrentapp.service.BookingService;
import com.sda.carrentapp.service.CarManager;
import com.sda.carrentapp.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Period;
import java.util.List;
import java.util.Set;

@Controller
public class BookingController {

    private BookingService bookingService;
    private CarManager carManager;
    private DepartmentService departmentService;
    private UserBooking userBooking;

    public BookingController(BookingService bookingService, CarManager carManager, DepartmentService departmentService, UserBooking userBooking) {
        this.bookingService = bookingService;
        this.carManager = carManager;
        this.departmentService = departmentService;
        this.userBooking = userBooking;
    }

    @GetMapping("/booking/allBookings")
    public String bookingsView(Model model) {
        model.addAttribute("booked", bookingService.getBookings());
        return "bookings";
    }

//    @GetMapping("/booking/selectDateAndLocation")
//    public String bookingView(Model model) {
//        Booking booking = new Booking();
//        List<Department> departments = departmentService.getDepartments();
//        model.addAttribute("booking", booking);
//        model.addAttribute("departments", departments);
//        return "booking";
//    }

    @GetMapping("/booking/selectDateAndLocation")
    public String bookingView(Model model) {
       UserBooking userBooking = new UserBooking();
//        Booking booking = new Booking();
        List<Department> departments = departmentService.getDepartments();
        model.addAttribute("userBooking", userBooking);
        model.addAttribute("departments", departments);
        return "booking";
    }

    // todo: query cars by department, date and status
    @PostMapping("/booking/selectCar")
    public String carView(@ModelAttribute("userBooking") UserBooking userBooking, Model model) {
//        userBooking.mapBooking(booking);
        Set<Car> carsByRentDepAndDateAndStatus = carManager.getCarsByRentDepAndDateAndStatus(userBooking.getStartDate(), userBooking.getRentDepartment());
        int days = Period.between(userBooking.getStartDate(), userBooking.getEndDate()).getDays();
        model.addAttribute("days", days);
        model.addAttribute("cars", carsByRentDepAndDateAndStatus);
        model.addAttribute("userBooking", userBooking);
        return "cars";
    }

    @PostMapping("/booking/selectCar/{id}")
    public String addCarToBooking(@PathVariable Long id, @ModelAttribute("userBooking") UserBooking userBooking) {
        Car selectedCar = carManager.getCarById(id);
        userBooking.setCar(selectedCar);
        bookingService.addBooking(userBooking);
//        userBooking.clear();
        return "redirect:/booking/allBookings";
    }

    @PostMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable Long id) throws BookingNotFoundException {
        bookingService.deleteBooking(id);
        return "redirect:/booking/allBookings";
    }

    @GetMapping("/booking/update/{id}")
    public String updateBooking(@PathVariable Long id, Model model) throws BookingNotFoundException {
        bookingService.deleteBooking(id);
        model.addAttribute("userBooking", bookingService.getBookingById(id));
        model.addAttribute("departments", departmentService.getDepartments());
        return "booking";
    }

    @PostMapping("/booking/activate/{id}")
    public String activateRent(@PathVariable Long id) throws BookingNotFoundException {
        bookingService.acceptRent(id);
        return "redirect:/booking/allBookings";
    }

    @PostMapping("/booking/return/{id}")
    public String returnCar(@PathVariable Long id, Model model) throws BookingNotFoundException {
        try {
            bookingService.returnCar(id);
        } catch (RentStartDateIsNullException e) {
            e.printStackTrace();
            model.addAttribute("url", "/booking/allBookings");
            model.addAttribute("message",
                    new Message("Warning", "Rent has not been started yet"));
            return "message";
        }
        return "redirect:/booking/allBookings";
    }
}
