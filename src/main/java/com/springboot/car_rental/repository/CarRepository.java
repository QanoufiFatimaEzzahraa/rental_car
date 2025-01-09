package com.springboot.car_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.car_rental.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

}
