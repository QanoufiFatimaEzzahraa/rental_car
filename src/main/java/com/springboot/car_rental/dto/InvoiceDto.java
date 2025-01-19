package com.springboot.car_rental.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InvoiceDto {
    private Long bookingId;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private double pricePerDay;
    private double totalPrice;
    private String userName;
    private String userEmail;
    private String carDetails;
}
