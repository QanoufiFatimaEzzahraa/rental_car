package com.springboot.car_rental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car_rental.dto.CarDto;
import com.springboot.car_rental.entity.Car;
import com.springboot.car_rental.service.AdminCarService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminCarController {
	
	private final AdminCarService adminCarService;
	
    @PostMapping("/car")
    public ResponseEntity<?> postCar(@ModelAttribute CarDto carDto) throws IOException {
        boolean isSuccessful = adminCarService.postCar(carDto);

        if (isSuccessful) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
	@GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
		return ResponseEntity.ok(adminCarService.getAllCars());
    }
	
	@DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
		adminCarService.deleteCar(id);
        return ResponseEntity.ok(null);
    }
	
	@GetMapping("/car/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(adminCarService.getCarById(id));
    }
	
	 @PutMapping("/car/{id}")
	    public ResponseEntity<Void> updateCar(@PathVariable Long id, @ModelAttribute CarDto carDto) throws IOException {
	        try {
	            boolean isSuccessful = adminCarService.updateCar(id, carDto);

	            if (isSuccessful) {
	                return ResponseEntity.status(HttpStatus.OK).build();
	            }

	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	        }
	    }

}
