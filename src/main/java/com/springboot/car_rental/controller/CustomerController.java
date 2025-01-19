package com.springboot.car_rental.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.springboot.car_rental.dto.BookACarDto;
import com.springboot.car_rental.dto.CarDto;
import com.springboot.car_rental.dto.UpdateUserProfileDto;
import com.springboot.car_rental.dto.UserProfileDto;
import com.springboot.car_rental.entity.BookACar;
import com.springboot.car_rental.entity.User;
import com.springboot.car_rental.repository.BookACarRepository;
import com.springboot.car_rental.repository.UserRepository;
import com.springboot.car_rental.service.CustomerService;
import com.springboot.car_rental.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {
	
	@Autowired
    private CustomerService customerService;
	
	@Autowired
    private  UserRepository userRepository;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
    private BookACarRepository bookACarRepository;
	
	

	@GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile() {
        // Récupérer l'utilisateur connecté depuis le SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Récupérer l'email de l'utilisateur

        // Appeler le service pour obtenir les informations du profil
        UserProfileDto userProfile = customerService.getProfile(userEmail);
        return ResponseEntity.ok(userProfile);
    }
	
	@PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateUserProfile(@RequestBody UpdateUserProfileDto userProfileDto) {
        // Récupérer l'utilisateur connecté depuis le SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Récupérer l'email de l'utilisateur

        // Appeler le service pour mettre à jour le profil
        UserProfileDto updatedProfile = customerService.updateProfile(userEmail, userProfileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    
	@GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(customerService.getAllCars());
    }
	
	@PostMapping("/car/book")
	public ResponseEntity<BookACar> bookACar(@RequestBody BookACarDto bookACarDto) {
	    BookACar savedBooking = customerService.bookACar(bookACarDto);

	    // Vérifiez si la réservation a été effectuée avec succès
	    if (savedBooking != null) {
	        // Retournez la réservation complète avec les détails, y compris le prix
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
	    }

	    // Si la réservation échoue, retournez une erreur avec un statut BAD_REQUEST
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	
	@GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long carId) {
        CarDto carDto = customerService.getCarById(carId);

        if (carDto != null) {
            return ResponseEntity.ok(carDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
	
	@GetMapping("/car/bookings/{userId}")
    public ResponseEntity<List<BookACarDto>> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }
	
	/**@GetMapping("/car/book/{bookId}")
	public ResponseEntity<BookACar> getBookingById(@PathVariable Long bookId) {
	    Optional<BookACar> booking = bookACarRepository.findById(bookId);

	    if (booking.isPresent()) {
	        return ResponseEntity.ok(booking.get());
	    }
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}**/


}
