package com.springboot.car_rental.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.car_rental.dto.UserDTO;
import com.springboot.car_rental.entity.User;
import com.springboot.car_rental.enums.UserRole;
import com.springboot.car_rental.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(UserDTO userDTO, boolean isAdmin, User currentUser) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        UserRole role = UserRole.CLIENT;
        if (isAdmin) {
            if (currentUser == null || !currentUser.getRole().equals(UserRole.ADMIN)) {
                throw new RuntimeException("Vous n'êtes pas autorisé à créer un administrateur !");
            }
        role = UserRole.ADMIN;
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
        user.setRole(role);

        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable !"));
        
     // Vérifier si le mot de passe est null ou vide
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Mot de passe manquant !");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect !");
        }

        return user;
    }

    public User updateUser(UserDTO userDTO, String email) {
    	
    	Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable !"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());

        return userRepository.save(user);
    }
    
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable !"));
    }

}
