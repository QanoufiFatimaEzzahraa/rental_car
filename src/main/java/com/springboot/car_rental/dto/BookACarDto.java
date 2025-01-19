package com.springboot.car_rental.dto;

import lombok.Data;
import java.util.Date;

import com.springboot.car_rental.enums.BookCarStatus;

@Data
public class BookACarDto {
	
	private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private double pricePerDay;
    private double price;
    private BookCarStatus BookCarStatus;
    private Long carId;
    private Long userId;
    private String username;
    private String email;

}
