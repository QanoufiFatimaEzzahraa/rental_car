package com.springboot.car_rental.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int carId;

    private String brand;              // Marque de la voiture
    private String model;              // Modèle de la voiture
    private int year;                  // Année de fabrication
    private String licensePlate;       // Plaque d'immatriculation
    private String status;             // Statut de la voiture (ex: disponible, en location, maintenance)
    private double pricePerDay;        // Prix par jour de location
    private String description;        // Description du véhicule
    
 // Liste des URLs d'images liées à la voiture
    @ElementCollection
    private List<String> images;

}
