package com.springboot.car_rental.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.springboot.car_rental.dto.CarDto;
import com.springboot.car_rental.dto.ListCarDto;
import com.springboot.car_rental.dto.SearchCarDto;
import com.springboot.car_rental.entity.Car;
import com.springboot.car_rental.repository.CarRepository;

import io.jsonwebtoken.io.IOException;

@Service
public class AdminCarService {
	
	@Autowired
    private CarRepository carRepository;
	
	public boolean postCar(CarDto carDto) throws IOException {
        boolean isSuccessful = false;

        try {
            Car car = new Car();
            car.setBrand(carDto.getBrand());
            car.setModel(carDto.getModel());
            car.setYear(carDto.getYear());
            car.setLicensePlate(carDto.getLicensePlate());
            car.setPricePerDay(carDto.getPricePerDay());
            car.setDescription(carDto.getDescription());
            car.setImage(carDto.getImage().getBytes());

            carRepository.save(car);

            isSuccessful = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSuccessful;
    }
	
	public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }
	
	public CarDto getCarById(Long id) {
        return carRepository.findById(id).map(Car::getCarDto).orElse(null); // map() is a method that applies a given function to each element of a stream
    }
	
	public boolean updateCar(Long id, CarDto carDto) throws IOException, java.io.IOException {
        Optional<Car> optionalCar = carRepository.findById(id);

        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();

            if (carDto.getImage() != null) {
                existingCar.setImage(carDto.getImage().getBytes());
            }

            existingCar.setBrand(carDto.getBrand());
            existingCar.setModel(carDto.getModel());
            existingCar.setYear(carDto.getYear());
            existingCar.setLicensePlate(carDto.getLicensePlate());
            existingCar.setPricePerDay(carDto.getPricePerDay());
            existingCar.setDescription(carDto.getDescription());

            carRepository.save(existingCar);

            return true;
        }

        return false;
    }
	
	public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
	
	public ListCarDto searchCar(SearchCarDto searchCarDto) {
		
		System.out.println("Requête reçue: " + searchCarDto);
	    

	    
	 
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setModel(searchCarDto.getModel());
        car.setPricePerDay(searchCarDto.getPricePerDay());


        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
        		.withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        		.withMatcher("model", ExampleMatcher.GenericPropertyMatchers.exact())
        		.withMatcher("pricePerDay", ExampleMatcher.GenericPropertyMatchers.exact());

        Example<Car> carExample = Example.of(car, exampleMatcher);

        List<Car> carList = carRepository.findAll(carExample);

        ListCarDto listCarDto = new ListCarDto();
        listCarDto.setListCarDto(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));
        
        System.out.println(carList.size());
        return listCarDto;
    }

}
