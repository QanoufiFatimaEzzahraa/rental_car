package com.springboot.car_rental.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.car_rental.dto.BookACarDto;
import com.springboot.car_rental.dto.SearchCarDto;
import com.springboot.car_rental.service.AdminBookACarService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminBookACarController {
	
	@Autowired 
	private AdminBookACarService adminBookACarService;

	
	@GetMapping("/car/bookings")
    private ResponseEntity<List<BookACarDto>> getBookings() {
        return ResponseEntity.ok(adminBookACarService.getBookings());
    }

    @GetMapping("/car/booking/{bookingId}/{status}")
    private ResponseEntity<Void> changeBookingStatus(@PathVariable Long bookingId, @PathVariable String status) {
        boolean isSuccessful = adminBookACarService.changeBookingStatus(bookingId, status);

        if (isSuccessful) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
   
}
