package com.sda.carrentapp.controller;

import com.sda.carrentapp.entity.User;
import com.sda.carrentapp.entity.UserDTO;
import com.sda.carrentapp.exception.UserNotFoundException;
import com.sda.carrentapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/addUser")
    public String addUserView(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("userDTO") UserDTO userDTO) {
        userService.saveUser(userDTO);
        return "redirect:/users";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUserView(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        model.addAttribute("user", userService.getUserById(id));
        return "user-form";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
