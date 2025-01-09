package com.springboot.car_rental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.car_rental.entity.BookACar;

@Repository
public interface BookACarRepository extends JpaRepository<BookACar, Long> {

    List<BookACar> findAllByUserId(Long userId);
}
