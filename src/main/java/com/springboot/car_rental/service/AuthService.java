package com.springboot.car_rental.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.car_rental.dto.AuthenticationRequest;
import com.springboot.car_rental.dto.AuthenticationResponse;
import com.springboot.car_rental.dto.UserDTO;
import com.springboot.car_rental.entity.User;
import com.springboot.car_rental.enums.UserRole;
import com.springboot.car_rental.service.jwt.UserService;
import com.springboot.car_rental.util.JwtUtil;
import com.springboot.car_rental.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private JwtUtil jwtUtil;

	
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;

    public User signupCustomer(UserDTO userDTO) {
		
		Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }
        
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new RuntimeException("Mot de passe manquant !");
        }
        
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        
        return userRepository.save(user);
    }

    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException {
    	try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Incorrect Email Or Password.");
        }
    	
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    
    }

}
