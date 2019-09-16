package com.sda.carrentapp.controller;

import com.sda.carrentapp.exception.UserNotFoundException;
import com.sda.carrentapp.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userSettings")
public class UserSettingsController {

    private UserService userService;

    public UserSettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String showUserSettings(@PathVariable("username") String userName, Model model) throws UserNotFoundException {
        model.addAttribute("user", userService.getUserByUserName(userName));
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "user-form";
    }
}
