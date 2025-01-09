package com.springboot.car_rental.dto;

import lombok.Data;

@Data
public class SearchCarDto {
	private String brand;              // Marque de la voiture
    private String model;              // Modèle de la voiture
    private double pricePerDay;
}
