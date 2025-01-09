package com.springboot.car_rental.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.car_rental.dto.CarDto;
import com.springboot.car_rental.enums.CarStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String brand;              // Marque de la voiture
    private String model;              // Modèle de la voiture
    private int year;                  // Année de fabrication
    private String licensePlate;       // Plaque d'immatriculation
    //private CarStatus status;             // Statut de la voiture (ex: disponible, en location, maintenance)
    private double pricePerDay;        // Prix par jour de location
    private String description;        // Description du véhicule
    
    @Column(columnDefinition = "longblob")
    private byte[] image;

    public CarDto getCarDto() {
        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setBrand(brand);
        carDto.setModel(model);
        carDto.setYear(year);
        carDto.setLicensePlate(licensePlate);
        carDto.setPricePerDay(pricePerDay);
        carDto.setDescription(description);
        carDto.setReturnedImage(image);
        
        return carDto;
    }

}
