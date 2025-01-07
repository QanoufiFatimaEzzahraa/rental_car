package com.springboot.car_rental.dto;
import com.springboot.car_rental.enums.UserRole;

import lombok.Data;

@Data
public class UserDTO {
	
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private UserRole userRole;
    
    
}

