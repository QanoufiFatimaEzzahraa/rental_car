package com.springboot.car_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.car_rental.entity.Car;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findByLicensePlate(String licensePlate); // Trouver une voiture par plaque d'immatriculation
}
