package com.sda.carrentapp.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
    private String address;
    private EntityStatus entityStatus;
    private List<Booking> booking;

}
