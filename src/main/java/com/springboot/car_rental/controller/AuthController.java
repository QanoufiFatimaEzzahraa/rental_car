package com.springboot.car_rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car_rental.service.AuthService;
import com.springboot.car_rental.util.JwtUtil;
import com.springboot.car_rental.dto.AuthenticationRequest;
import com.springboot.car_rental.dto.AuthenticationResponse;
import com.springboot.car_rental.dto.UserDTO;
import com.springboot.car_rental.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupCustomer(@RequestBody UserDTO userDTO) {
		User newUser = authService.signupCustomer(userDTO);
        return ResponseEntity.ok(newUser);
    }
	
	
	@PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) {
    	
           AuthenticationResponse newAuth = authService.loginUser( authenticationRequest);
           return ResponseEntity.ok(newAuth);
           
    		
    	}
    }
	
	
