package com.springboot.car_rental.controller;


import com.springboot.car_rental.dto.UserDTO;
import com.springboot.car_rental.entity.User;
import com.springboot.car_rental.service.UserService;
import com.springboot.car_rental.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Autorise les requêtes CORS (utiles pour Postman ou une application frontale)
public class UserController {
	
	 @Autowired
	    private UserService userService;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @PostMapping("/signup")
	    public ResponseEntity<User> registerUser(
	            @RequestBody UserDTO userDTO,
	            @RequestParam(value = "isAdmin", required = false, defaultValue = "false") boolean isAdmin,
	            @RequestHeader(value = "Authorization", required = false) String token) {

	        User currentUser = null;
	        if (isAdmin) {
	            if (token == null || !token.startsWith("Bearer ")) {
	                throw new RuntimeException("Token manquant ou invalide !");
	            }
	            String jwt = token.substring(7);
	            String email = jwtUtil.extractEmail(jwt);
	            currentUser = userService.getUserByEmail(email);// Charger l'utilisateur courant
	        }

	        User newUser = userService.registerUser(userDTO, isAdmin, currentUser);
	        return ResponseEntity.ok(newUser);
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
	    	
	    	try {
	           User user = userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
	           String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
	           return ResponseEntity.ok(token);
	           
	    	}catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	    		
	    	}
	    }

	    @GetMapping("/profile")
	    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token) {
	        String email = jwtUtil.extractEmail(token);
	        User user = userService.getUserByEmail(email);
	        return ResponseEntity.ok(user);
	    }
	    
	    
	    @PutMapping("/profile")
	    public ResponseEntity<User> updateUserProfile(@RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token) {
	        String email = jwtUtil.extractEmail(token);
	        User user = userService.updateUser(userDTO, email);
	        return ResponseEntity.ok(user);
	    }
	    
	    
	    /*@GetMapping("/rentals")
	    public ResponseEntity<List<Rental>> getUserRentals(@RequestHeader("Authorization") String token) {
	        String email = jwtUtil.extractEmail(token);
	        List<Rental> rentals = rentalService.getRentalsByUserEmail(email);
	        return ResponseEntity.ok(rentals);
	    }
	    
	    @GetMapping("/admin/rentals")
	    public ResponseEntity<List<Rental>> getAllRentals(@RequestHeader("Authorization") String token) {
	        String adminEmail = jwtUtil.extractEmail(token);
	        User admin = userService.getUserByEmail(adminEmail);
	        if (!admin.getRole().equals("ADMIN")) {
	            throw new RuntimeException("Accès interdit");
	        }
	        List<Rental> rentals = rentalService.getAllRentals();
	        return ResponseEntity.ok(rentals);
	    }*/






}
