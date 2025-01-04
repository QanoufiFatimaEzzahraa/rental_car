package com.springboot.car_rental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.car_rental.entity.Car;
import com.springboot.car_rental.repository.CarRepository;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    
    private static final String IMAGE_UPLOAD_DIR = "src/main/resources/static/images/";


    // Ajouter une nouvelle voiture
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    // Modifier une voiture existante
    public Car updateCar(int carId, Car carDetails) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setBrand(carDetails.getBrand());
            car.setModel(carDetails.getModel());
            car.setYear(carDetails.getYear());
            car.setLicensePlate(carDetails.getLicensePlate());
            car.setStatus(carDetails.getStatus());
            car.setPricePerDay(carDetails.getPricePerDay());
            car.setDescription(carDetails.getDescription());
            car.setImages(carDetails.getImages());
            return carRepository.save(car);
        }
        return null;  // Retourner null si la voiture n'est pas trouvée
    }

    // Supprimer une voiture
    public boolean deleteCar(int carId) {
        Optional<Car> existingCar  = carRepository.findById(carId);
        if (existingCar .isPresent()) {
            carRepository.delete(existingCar.get());
            return true;
        }
        return false;  // Si la voiture n'existe pas
    }

    // Obtenir toutes les voitures
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Obtenir une voiture par sa plaque d'immatriculation
    public Optional<Car> getCarByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate);
    }
    
 // Obtenir une voiture par sa plaque d'immatriculation
    public Optional<Car> getCarById(int id) {
        return carRepository.findById(id);
    }
    
   
    // Méthode pour télécharger une image et obtenir l'URL
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path targetLocation = Path.of(IMAGE_UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return "/images/" + fileName; // L'URL relative pour l'image
    }
}
