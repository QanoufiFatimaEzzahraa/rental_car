package com.springboot.car_rental.repository;

import com.springboot.car_rental.entity.User;
import com.springboot.car_rental.enums.UserRole;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    User findByUserRole(UserRole userRole);

    
}
