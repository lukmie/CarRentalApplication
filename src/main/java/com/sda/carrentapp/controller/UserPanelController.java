package com.sda.carrentapp.controller;

import com.sda.carrentapp.entity.User;
import com.sda.carrentapp.exception.BookingNotFoundException;
import com.sda.carrentapp.exception.UserNotFoundException;
import com.sda.carrentapp.service.BookingService;
import com.sda.carrentapp.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userPanel")
public class UserPanelController {

    private UserService userService;
    private BookingService bookingService;

    public UserPanelController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @PostMapping("/accountSettings")
    public String showUserSettings(Model model) throws UserNotFoundException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userService.getUserByUserName(userName));
        model.addAttribute("username", userName);
        return "user-form";
    }

    @RequestMapping("/bookings")
    public String showUserBookings(@ModelAttribute("user") User user, Model model) throws UserNotFoundException {
        bookingsViewModelAttributes(model);
        return "bookings";
    }

    @PostMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable Long id, Model model) throws BookingNotFoundException, UserNotFoundException {
        bookingService.deleteBooking(id);
        bookingsViewModelAttributes(model);
        return "redirect:/userPanel/bookings";
    }

    private void bookingsViewModelAttributes(Model model) throws UserNotFoundException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("booked", bookingService.getAllBookingsByUserName(userName));
        model.addAttribute("user", userService.getUserByUserName(userName));
        model.addAttribute("username", userName);
    }
}
