package com.springboot.car_rental.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.car_rental.dto.BookACarDto;
import com.springboot.car_rental.dto.CarDto;
import com.springboot.car_rental.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {
	
	@Autowired
    private CustomerService customerService;

	
	@GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(customerService.getAllCars());
    }
	
	@PostMapping("/car/book")
    public ResponseEntity<Void> bookACar(@RequestBody BookACarDto bookACarDto) {
        boolean isSuccessful = customerService.bookACar(bookACarDto);

        if (isSuccessful) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
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

}
