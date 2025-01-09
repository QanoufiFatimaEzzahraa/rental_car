package com.springboot.car_rental.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.springboot.car_rental.dto.BookACarDto;
import com.springboot.car_rental.dto.CarDto;
import com.springboot.car_rental.dto.ListCarDto;
import com.springboot.car_rental.dto.SearchCarDto;
import com.springboot.car_rental.entity.BookACar;
import com.springboot.car_rental.entity.Car;
import com.springboot.car_rental.enums.BookCarStatus;
import com.springboot.car_rental.repository.BookACarRepository;
import com.springboot.car_rental.repository.CarRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminBookACarService {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
    private BookACarRepository bookACarRepository;
	
	
	private CarDto carDto;
	
	private ListCarDto listCarDto;
	
	
	public List<BookACarDto> getBookings() {
        return bookACarRepository.findAll().stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
    }
	
	public boolean changeBookingStatus(Long id, String status) {
        Optional<BookACar> optionalBookACar = bookACarRepository.findById(id);

        if (optionalBookACar.isPresent()) {
            BookACar bookACar = optionalBookACar.get();

            if (Objects.equals(status, "Approve")) {
                bookACar.setBookCarStatus(BookCarStatus.APPROVED);
            } else {
                bookACar.setBookCarStatus(BookCarStatus.REJECTED);
            }

            bookACarRepository.save(bookACar);

            return true;
        }

        return false;
    }
	


	public ListCarDto searchCar(SearchCarDto searchCarDto) {
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setModel(searchCarDto.getModel());
        car.setPricePerDay(searchCarDto.getPricePerDay());

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        		.withMatcher("model", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        		.withMatcher("pricePerDay", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        		.withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Car> carExample = Example.of(car, exampleMatcher);

        List<Car> carList = carRepository.findAll(carExample);

        ListCarDto listCarDto = new ListCarDto();
        listCarDto.setListCarDto(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));

        return listCarDto;
    }

}
