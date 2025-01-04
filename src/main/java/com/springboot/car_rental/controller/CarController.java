package com.springboot.car_rental.controller;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.car_rental.entity.Car;
import com.springboot.car_rental.service.CarService;

@RestController
@RequestMapping("/api/admin/cars")
@CrossOrigin(origins = "*") // Autorise les requêtes CORS (utiles pour Postman ou une application frontale)

public class CarController {

    @Autowired
    private CarService carService;

    // Ajouter une nouvelle voiture
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        try {
            Car newCar = carService.addCar(car);
            return new ResponseEntity<>(newCar, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Modifier une voiture existante
    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable int carId, @RequestBody Car carDetails) {
        try {
            Car updatedCar = carService.updateCar(carId, carDetails);
            if (updatedCar != null) {
                return new ResponseEntity<>(updatedCar, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 // Upload une image pour une voiture
    @PostMapping("/{carId}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable int carId, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = carService.uploadImage(file); // Appel à la méthode du service pour stocker l'image
            Optional<Car> car = carService.getCarById(carId);
            if (car.isPresent()) {
                Car updatedCar = car.get();
                updatedCar.getImages().add(imageUrl); // Ajouter l'URL de l'image à la liste des images de la voiture
                carService.updateCar(carId, updatedCar); // Mettre à jour la voiture avec l'URL de l'image
                return new ResponseEntity<>(imageUrl, HttpStatus.OK); // Retourner l'URL de l'image
            } else {
                return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Error uploading image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Supprimer une voiture
    @DeleteMapping("/{carId}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable int carId) {
        try {
            boolean isDeleted = carService.deleteCar(carId);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtenir toutes les voitures
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        try {
            List<Car> cars = carService.getAllCars();
            if (cars.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtenir une voiture par sa plaque d'immatriculation
    @GetMapping("/licensePlate/{licensePlate}")
    public ResponseEntity<Car> getCarByLicensePlate(@PathVariable String licensePlate) {
        try {
            Optional<Car> car = carService.getCarByLicensePlate(licensePlate);
            if (car.isPresent()) {
                return new ResponseEntity<>(car.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}