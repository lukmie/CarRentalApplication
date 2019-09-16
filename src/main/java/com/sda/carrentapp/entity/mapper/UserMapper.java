package com.sda.carrentapp.entity.mapper;

import com.sda.carrentapp.entity.User;
import com.sda.carrentapp.entity.UserDTO;

public class UserMapper {
    public static User map(UserDTO userDTO){
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setEntityStatus(userDTO.getEntityStatus());
        user.setBooking(userDTO.getBooking());

        return user;
    }
}
