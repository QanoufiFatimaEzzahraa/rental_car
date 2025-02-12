package com.springboot.car_rental.dto;

import lombok.Data;

@Data
public class UpdateUserProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}