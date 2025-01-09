package com.springboot.car_rental.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.car_rental.enums.CarStatus;
import com.springboot.car_rental.enums.UserRole;

import lombok.Data;

@Data
public class CarDto {
	
	private Long id;
	private String brand;              // Marque de la voiture
    private String model;              // Modèle de la voiture
    private int year;                  // Année de fabrication
    private String licensePlate;       // Plaque d'immatriculation
    //private CarStatus status;             // Statut de la voiture (ex: disponible, en location, maintenance)
    private double pricePerDay;        // Prix par jour de location
    private String description;        // Description du véhicule
    
    private MultipartFile image;
    private byte[] returnedImage; 
    

}
