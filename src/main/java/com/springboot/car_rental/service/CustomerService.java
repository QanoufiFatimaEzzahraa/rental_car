package com.springboot.car_rental.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.car_rental.dto.BookACarDto;
import com.springboot.car_rental.dto.CarDto;
import com.springboot.car_rental.entity.BookACar;
import com.springboot.car_rental.entity.Car;
import com.springboot.car_rental.entity.User;
import com.springboot.car_rental.enums.BookCarStatus;
import com.springboot.car_rental.repository.BookACarRepository;
import com.springboot.car_rental.repository.CarRepository;
import com.springboot.car_rental.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CustomerService {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private BookACarRepository bookACarRepository;
	
	public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }
	
	public boolean bookACar(BookACarDto bookACarDto) {
        Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
        Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());

        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            Car existingCar = optionalCar.get();

            BookACar bookACar = new BookACar();
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);

            long diffInMilliSeconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
            long days = TimeUnit.MICROSECONDS.toDays(diffInMilliSeconds);

            bookACar.setDays(days);
            bookACar.setPrice((long) (days * existingCar.getPricePerDay()));

            bookACarRepository.save(bookACar);
            return true;
        }

        return false;
    }
	
	public CarDto getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }
	
	public List<BookACarDto> getBookingsByUserId(Long userId) {
        return bookACarRepository.findAllByUserId(userId).stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
    }

}
